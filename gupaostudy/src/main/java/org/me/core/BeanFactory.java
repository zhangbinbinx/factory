package org.me.core;

public interface BeanFactory {
    Object  getBean(String beanName) throws  Exception;
    Object getBean(Class<?> className) throws  Exception;

}
