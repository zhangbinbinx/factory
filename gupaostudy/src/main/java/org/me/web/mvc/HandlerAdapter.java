package org.me.web.mvc;

import org.me.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HandlerAdapter {
    public boolean isSupport(Object handler){
        return handler instanceof HandlerMapping;
    }
    public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception{
        HandlerMapping handlerMapping = (HandlerMapping)handler;

        //把方法的形参列表和request的参数列表所在顺序进行一一对应
        Map<String,Integer> paramIndexMapping = new HashMap<String, Integer>();

        Annotation[] [] pa = handlerMapping.getMethod().getParameterAnnotations();
        for (int i = 0; i < pa.length ; i ++) {
            for(Annotation a : pa[i]){
                if(a instanceof RequestParam){
                    String paramName = ((RequestParam) a).value();
                    if(!"".equals(paramName.trim())){
                        paramIndexMapping.put(paramName, i);
                    }
                }
            }
        }

        //提取方法中的request和response参数
        Class<?> [] paramsTypes = handlerMapping.getMethod().getParameterTypes();
        for (int i = 0; i < paramsTypes.length ; i ++) {
            Class<?> type = paramsTypes[i];
            if(type == HttpServletRequest.class ||
                    type == HttpServletResponse.class){
                paramIndexMapping.put(type.getName(),i);
            }
        }

        //获得方法的形参列表
        Map<String,String[]> params = req.getParameterMap();

        //实参列表
        Object [] paramValues = new Object[paramsTypes.length];

        for (Map.Entry<String, String[]> parm : params.entrySet()) {
            String value = Arrays.toString(parm.getValue()).replaceAll("\\[|\\]","")
                    .replaceAll("\\s",",");

            if(!paramIndexMapping.containsKey(parm.getKey())){continue;}

            int index = paramIndexMapping.get(parm.getKey());
            paramValues[index] = caseStringValue(value,paramsTypes[index]);
        }

        if(paramIndexMapping.containsKey(HttpServletRequest.class.getName())) {
            int reqIndex = paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = req;
        }

        if(paramIndexMapping.containsKey(HttpServletResponse.class.getName())) {
            int respIndex = paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[respIndex] = resp;
        }

        Object result = handlerMapping.getMethod().invoke(handlerMapping.getController(),paramValues);
        if(result == null || result instanceof Void){ return null; }

        boolean isModelAndView = handlerMapping.getMethod().getReturnType() == ModelAndView.class;
        if(isModelAndView){
            return (ModelAndView) result;
        }
        return null;
    }
    private Object caseStringValue(String value, Class<?> paramsType) {
        if(String.class == paramsType){
            return value;
        }
        if(Integer.class == paramsType){
            return Integer.valueOf(value);
        }
        else if(Double.class == paramsType){
            return Double.valueOf(value);
        }else {
            if(value != null){
                return value;
            }
            return null;
        }

    }
}
