package org.me.aop;

import org.me.aop.support.AdrvisedSupport;

public class CglibAopProxy implements AopProxy {
    private AdrvisedSupport config;

    public CglibAopProxy(AdrvisedSupport config) {
        this.config = config;
    }

    public Object getProxy() {
        return null;
    }

    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
