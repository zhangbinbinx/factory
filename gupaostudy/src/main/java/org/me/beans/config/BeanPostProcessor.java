package org.me.beans.config;

public class BeanPostProcessor {
    public Object postProcessorBeforInitialization(Object bean,String beanName) throws Exception{
        return bean;

    }
    public Object postProcessAfterInitialization(Object bean,String beanName) throws Exception{
        return bean;
    }
}
