package org.me.context;

import org.me.beans.BeanWrapper;
import org.me.beans.DefaultListAbleBeanFactory;
import org.me.beans.config.BeanDefinition;
import org.me.beans.support.BeanDefinitionReader;
import org.me.core.BeanFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext extends DefaultListAbleBeanFactory implements BeanFactory {
    private String [] configLocations;
    private BeanDefinitionReader reader;
    //单例的 IOC 容器缓存
    private Map<String,Object> sigletonObjects = new ConcurrentHashMap<String, Object>();
    private Map<String,BeanWrapper> factoryBeanInstaceCache = new ConcurrentHashMap<String,BeanWrapper>();

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

    }

    public Object getBean(String beanName) throws Exception{
        return getBean(beanName);
    }

    public Object getBean(Class<?> className)throws Exception {
        return getBean(className);
    }
}
