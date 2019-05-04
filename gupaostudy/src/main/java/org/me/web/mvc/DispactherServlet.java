package org.me.web.mvc;

import com.spring.annotation.Autowired;
import com.spring.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.me.annotation.Controller;
import org.me.annotation.RequestMapping;
import org.me.context.ApplicationContext;

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
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Slf4j
public class DispactherServlet extends HttpServlet {

    private final String LOCATION = "contextConfigLocation";
    private List<HandlerMapping> handlerMappingList = new ArrayList<HandlerMapping>();
    private Map<HandlerMapping,HandlerAdapter> adapter = new ConcurrentHashMap<HandlerMapping, HandlerAdapter>();
    private List<ViewResolver> viewResolverList = new ArrayList<ViewResolver>();
    private ApplicationContext applicationContext;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       try {
           doDispatch(req,resp);
       }catch (Exception e){
           resp.getWriter().write("<font size='25' color='blue'>500 Exception</font><br/>Details:<br/>" + Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]","")
                   .replaceAll("\\s","\r\n") + "<font color='green'><i>Copyright@GupaoEDU</i></font>");
           e.printStackTrace();
       }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        HandlerMapping handler = getHandler(req);
        if(handler == null){
            processDispatchResult(req,resp,new ModelAndView("404"));
            return;
        }
        HandlerAdapter adapter = getHandlerAdapter(handler);
        ModelAndView mv = adapter.handle(req,resp,handler);
        processDispatchResult(req,resp,mv);
    }

    private HandlerAdapter getHandlerAdapter(HandlerMapping handler) {
        if(this.adapter.isEmpty()){return null;}
        HandlerAdapter ha = this.adapter.get(handler);
        if(ha.isSupport(handler)){
            return  ha;
        }
        return null;
    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, ModelAndView mv) {
        if(mv == null){
            return;
        }
        if(this.viewResolverList.isEmpty()){return;}
        if(this.viewResolverList != null){
            for (ViewResolver viewResolver : this.viewResolverList) {
                View view = viewResolver.resolverViewName(mv.getViewName(),null);
                //Spring中这里会去获取最合适的模板
                if(view != null){
                    try {
                        view.render(mv.getModel(),req,resp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }

        }
    }

    private HandlerMapping getHandler(HttpServletRequest req) {
        if(this.handlerMappingList.isEmpty()){return null;}
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath,"").replaceAll("/+","/");
        for (HandlerMapping handlerMapping : this.handlerMappingList) {
            Matcher matcher = handlerMapping.getPattern().matcher(url);
            if(!matcher.matches()){continue;}
            return handlerMapping;
        }
        return null;

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
      applicationContext = new ApplicationContext(config.getInitParameter(LOCATION));
      initStrategies(applicationContext);
    }

    private void initStrategies(ApplicationContext applicationContext) {
        initMultiPartResolver(applicationContext);
        initLocalResolver(applicationContext);
        initThemeResolver(applicationContext);
        initHandlerMapping(applicationContext);
        initHandlerAdapter(applicationContext);
        initHanderExceptionResolver(applicationContext);
        initRequestToViewNameTranslator(applicationContext);
        initViewResoler(applicationContext);
        initFlashMapResoler(applicationContext);
    }

    private void initFlashMapResoler(ApplicationContext applicationContext) {
    }

    private void initRequestToViewNameTranslator(ApplicationContext applicationContext) {
    }

    private void initViewResoler(ApplicationContext applicationContext) {
        String root = applicationContext.getConfig().getProperty("templateRoot");
        this.viewResolverList.add(new ViewResolver(root));
       /* String rootPath = this.getClass().getClassLoader().getResource(root).getFile();
        File rootDir = new File(rootPath);
        for (File file : rootDir.listFiles()) {
            String tempPath = file.getAbsolutePath();
            String path = tempPath.substring(tempPath.lastIndexOf("classes") + 8).replace("/","//");
            this.viewResolverList.add(new ViewResolver(root));
        }*/

    }

    private void initHanderExceptionResolver(ApplicationContext applicationContext) {
    }

    private void initHandlerAdapter(ApplicationContext applicationContext) {
        for (HandlerMapping handlerMapping : this.handlerMappingList) {
            this.adapter.put(handlerMapping,new HandlerAdapter());
        }

    }

    private void initThemeResolver(ApplicationContext applicationContext) {
    }

    private void initLocalResolver(ApplicationContext applicationContext) {
    }

    private void initMultiPartResolver(ApplicationContext applicationContext) {

    }

    private void initHandlerMapping(ApplicationContext applicationContext) {
        String [] beanNames = applicationContext.getBeanDefinitionNames();
        try {
            for (String beanName : beanNames) {
                Object controller = applicationContext.getBean(beanName);
                if(controller == null){continue;}
                Class<?> clazz = controller.getClass();
                if(!clazz.isAnnotationPresent(Controller.class)){continue;}
                String baseUrl = "";
                if(clazz.isAnnotationPresent(RequestMapping.class)){
                    RequestMapping req = clazz.getAnnotation(RequestMapping.class);
                    baseUrl = req.value();
                }
                Method [] methods = clazz.getMethods();
                for (Method m : methods) {
                    if(!m.isAnnotationPresent(RequestMapping.class)){continue;}
                    RequestMapping req = m.getAnnotation(RequestMapping.class);
                    String tempUrl = "/" + baseUrl + "/" + req.value();
                    String regex = tempUrl.replaceAll("\\*",".*").replaceAll("/+", "/");;
                    Pattern pattern = Pattern.compile(regex);
                    this.handlerMappingList.add(new HandlerMapping(pattern,controller,m));
                    log.info("Mapping:" + regex + "," + m.getName());
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

}
