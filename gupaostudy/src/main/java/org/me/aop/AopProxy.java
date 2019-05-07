package org.me.aop;



public interface AopProxy {
    Object getProxy();
    Object getProxy(ClassLoader classLoader);
}
