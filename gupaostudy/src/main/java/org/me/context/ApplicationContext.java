package org.me.context;

import lombok.Data;
import org.me.annotation.Autowired;
import org.me.annotation.Controller;
import org.me.annotation.Service;
import org.me.aop.AopConfig;
import org.me.aop.AopProxy;
import org.me.aop.CglibAopProxy;
import org.me.aop.DynamicAopProxy;
import org.me.aop.support.AdrvisedSupport;
import org.me.beans.BeanWrapper;
import org.me.beans.DefaultListAbleBeanFactory;
import org.me.beans.config.BeanDefinition;
import org.me.beans.config.BeanPostProcessor;
import org.me.beans.support.BeanDefinitionReader;
import org.me.core.BeanFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
@Data
public class ApplicationContext extends DefaultListAbleBeanFactory implements BeanFactory {
    private String [] configLocations;
    private BeanDefinitionReader reader;
    //单例的 IOC 容器缓存
    private Map<String,Object> sigletonBeanCacheMap = new ConcurrentHashMap<String, Object>();
    private Map<String,BeanWrapper> beanWrapperMap = new ConcurrentHashMap<String,BeanWrapper>();

    public ApplicationContext(String... configLocations) {
        this.configLocations = configLocations;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refresh() throws Exception{
        //定位
        reader = new BeanDefinitionReader(this.configLocations);

        //加载
        List<BeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
        //注册
        doRegisterBeanDefinition(beanDefinitions);
        //非延时的类提前加载
        doAutowared();
    }

    private void doRegisterBeanDefinition(List<BeanDefinition> beanDefinitions) throws Exception{
        for (BeanDefinition beanDefinition : beanDefinitions) {
            //  当一个接口有多个实现类时， 可能会有问题，需要测试
            if(super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())){
                throw new Exception("The “" + beanDefinition.getFactoryBeanName() + "” is exists!!");
            }
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
        }

    }

    private void doAutowared() {
        //注入时，需要先注入service，在注入controller
        for (Map.Entry<String, BeanDefinition> b : super.beanDefinitionMap.entrySet()) {
            String beanName = b.getKey();
            BeanDefinition beanDefinition = b.getValue();
            if(!beanDefinition.isLazyInit()){
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
       /* for (Map.Entry<String, BeanWrapper> b : beanWrapperMap.entrySet()) {
            String beanName = b.getKey();
            populateBean(beanName,b.getValue().getWrappedInstance());
        }*/


    }

    public Object getBean(String beanName) throws Exception{
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        BeanPostProcessor postProcessor = new BeanPostProcessor();
        Object instance = instantiaBean(beanDefinition);
        if(null == instance){return null;}
        postProcessor.postProcessorBeforInitialization(instance,beanName);
        BeanWrapper beanWrapper = new BeanWrapper(instance);
        this.beanWrapperMap.put(beanName,beanWrapper);
        //初始化  按照类的类名称
       /* Class<?>[] interfaces = instance.getClass().getInterfaces();
        for (Class<?> c : interfaces) {
            this.beanWrapperMap.put(c.getName(),beanWrapper);
        }*/
        postProcessor.postProcessAfterInitialization(instance,beanName);
        populateBean(beanName,instance);
        return this.beanWrapperMap.get(beanName).getWrappedInstance();
    }

    private void populateBean(String beanName, Object instance) {
        Class<?> clazz = instance.getClass();
        if(!clazz.isAnnotationPresent(Controller.class) && !clazz.isAnnotationPresent(Service.class)){return;}
        Field []fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            if(!f.isAnnotationPresent(Autowired.class)){continue;}
            Autowired autowired = f.getAnnotation(Autowired.class);
            String wirdBeanName = autowired.value().trim();
            if("".equals(wirdBeanName)){
                wirdBeanName = f.getType().getName();
            }
            f.setAccessible(true);
            try {
                f.set(instance,this.beanWrapperMap.get(wirdBeanName).getWrappedInstance());
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    private Object instantiaBean(BeanDefinition beanDefinition) {
        Object istance = null;
        String className = beanDefinition.getBeanClassName();
        try {
            if(this.sigletonBeanCacheMap.containsKey(className)){
                istance = this.sigletonBeanCacheMap.get(className);
                return istance;
            }else{
                Class<?> clazz = Class.forName(className);
                if(clazz.isInterface()){return null;}
                istance = clazz.newInstance();
                AdrvisedSupport config = instantionAopConfig(beanDefinition);
                config.setTargetClass(clazz);
                config.setTarget(istance);
                Object proxy = null;
                if(config.pointcutMatch()) {
                    istance = createProxy(config).getProxy();
                    //proxy = createProxy(config).getProxy();
                }
                //this.sigletonBeanCacheMap.put(beanDefinition.getFactoryBeanName(),proxy == null ? istance : proxy);
                this.sigletonBeanCacheMap.put(beanDefinition.getFactoryBeanName(),istance);
                return istance;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
    private AdrvisedSupport instantionAopConfig(BeanDefinition beanDefinition) throws Exception{
        AopConfig config = new AopConfig();
        config.setPointCut(reader.getConfig().getProperty("pointCut"));
        config.setAspectClass(reader.getConfig().getProperty("aspectClass"));
        config.setAspectBefore(reader.getConfig().getProperty("aspectBefore"));
        config.setAspectAfter(reader.getConfig().getProperty("aspectAfter"));
        config.setAspectAfterThrow(reader.getConfig().getProperty("aspectAfterThrow"));
        config.setAspectAfterThrowingName(reader.getConfig().getProperty("aspectAfterThrowingName"));
        return new AdrvisedSupport(config);
    }
    private AopProxy createProxy(AdrvisedSupport config) {
        Class targetClass = config.getTargetClass();
        if (targetClass.getInterfaces().length > 0) {
            return new DynamicAopProxy(config);
        }
        return new CglibAopProxy(config);
    }
    public Object getBean(Class<?> className)throws Exception {
        return getBean(className);
    }
    public String[] getBeanDefinitionNames(){
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanWrapperMap.size()]);
    }
    public Properties getConfig(){
        return this.reader.getConfig();
    }
}
