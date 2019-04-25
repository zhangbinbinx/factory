package org.me.beans.config;

import lombok.Data;

@Data
public class BeanDefinition {
    private String beanClassName;
    private boolean lazyInit;
    private String factoryBeanName;

}
