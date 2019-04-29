package org.me.beans;

import lombok.Data;
import org.me.beans.config.BeanDefinition;
import org.me.context.support.AbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListAbleBeanFactory extends AbstractApplicationContext {
    //存储注册信息的 BeanDefinition
    protected final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String,BeanDefinition>();

}
