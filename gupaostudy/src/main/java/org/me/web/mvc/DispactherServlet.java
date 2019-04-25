package org.me.web.mvc;

import com.spring.annotation.Autowired;
import com.spring.annotation.Controller;
import com.spring.annotation.RequestMapping;
import com.spring.annotation.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class DispactherServlet extends HttpServlet {
    private Properties contextConfig = new Properties();
    private List<String> classNames = new ArrayList<String>();
    private Map<String,Object> iocMap = new HashMap<String,Object>();
    private Map<String,Method> handlerMapping = new HashMap<String, Method>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //根据请求找到对应的url
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        uri = uri.replaceAll(contextPath,"").replaceAll("/+","/");
        Method method = handlerMapping.get(uri);
        if(method == null){
            resp.getWriter().write("404 page not found!!");
        }
        String beanName = getBeanName(method.getDeclaringClass().getSimpleName());
        try {
           Object object = method.invoke(iocMap.get(beanName),req.getParameter("name"));
            resp.getWriter().write(object.toString());
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write(e.getMessage());
        }
        //根据requestMapping信息，获取对应的method
        //反射调用method
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //第一步 加载配置
       loadConfig(config);

        //第二步 扫描包
        doScaner(contextConfig.getProperty("scanPackage"));
        //第三步 初始化容器
        initIocMap();
        //第四步 注入数据
        doDi();
        //第五步 初始化映射
        initHandlerMapping();
    }

    private void initHandlerMapping() {
        if(iocMap.isEmpty()){return;}
        for (Map.Entry<String, Object> s : iocMap.entrySet()) {
            //第一步 获取根路径
            String baseUrl = null;
            //第二步 获取方法映射
            Object o = s.getValue();
            if(o.getClass().isAnnotationPresent(Controller.class)){
                RequestMapping requestMapping = o.getClass().getAnnotation(RequestMapping.class);
                baseUrl =  "/" + requestMapping.value().trim() + "/";
                Method[]methods = o.getClass().getDeclaredMethods();
                for (Method method : methods) {
                    if(method.isAnnotationPresent(RequestMapping.class)){
                        requestMapping = method.getAnnotation(RequestMapping.class);
                        String url = requestMapping.value().trim();
                        url = (baseUrl + url).replaceAll("/+","/");
                        handlerMapping.put(url,method);
                    }
                }

            }

        }

    }

    private void doDi() {
        if(iocMap.isEmpty()){return;}

            // 拿到容器中类的所有属性

            try {
                for (Map.Entry<String, Object> s : iocMap.entrySet()) {
                    Field []fields = s.getValue().getClass().getDeclaredFields();
                    for (Field field : fields) {
                        if(field.isAnnotationPresent(Autowired.class)){
                            Autowired autowired = field.getAnnotation(Autowired.class);
                            String name = autowired.value().trim();
                            if("".equals(name)){
                                name = field.getType().getName();
                            }
                            field.setAccessible(true);
                            field.set(s.getValue(),iocMap.get(name));
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //判断属性是否包含Autowired,包含时注入属性
        }




    private void initIocMap() {
        if(classNames.isEmpty()){return;}
        try{
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                if(clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class)){
                    iocMap.put(getBeanName(clazz.getSimpleName()),clazz.newInstance());
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private String getBeanName(String name){
        char[] names = name.toCharArray();
        names[0] += 32;
        return String.valueOf(names);
    }
    private void doScaner(String scanPage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPage.replaceAll("\\.","/"));
        File classFile = new File(url.getFile());
        for (File file : classFile.listFiles()) {
            if(file.isDirectory()){
                doScaner(scanPage + "/" + file.getName());
            }
            if(!file.getName().endsWith(".class")){continue;}
            String className = scanPage + "." + file.getName().replace(".class","");
            classNames.add(className);
        }


    }

    private void loadConfig(ServletConfig config) {
        String contextConfigLocation = config.getInitParameter("contextConfiglocation");
        InputStream fis = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
