package org.me.aop;


import org.me.aop.intercept.MethodInvocation;
import org.me.aop.support.AdrvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class DynamicAopProxy implements AopProxy, InvocationHandler {
    private AdrvisedSupport config;

    public DynamicAopProxy(AdrvisedSupport config) {
        this.config = config;
    }

    public Object getProxy() {
        return getProxy(this.config.getTargetClass().getClassLoader());
    }

    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader,this.config.getClass().getInterfaces(),this);
    }

    public Object invoke(Object proxy, Method m, Object[] args) throws Exception{
        List<Object> interceptors = config.getInterceptorsAndDynamicInterceptionAdvise(m,this.config.getTargetClass());
        MethodInvocation methodInvocation = new MethodInvocation(proxy,this.config.getTarget(),m,args,this.config.getTargetClass(),interceptors);
        return methodInvocation.proceed();
    }
}
