package com.me.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FruitProxy implements InvocationHandler {
    public Object baiCaoYuan;
    public Object getInstance(Object baiCaoYuan){
        this.baiCaoYuan = baiCaoYuan;
        Class<?> clazz = baiCaoYuan.getClass();
        return Proxy.newProxyInstance(new MyClassLoader(),clazz.getInterfaces(),this);
    }

    @Override
    public Object invoke(Object o, Method m, Object[] args) throws InvocationTargetException, IllegalAccessException {
        before();
        Object obj = m.invoke(baiCaoYuan,args);
        after();
        return obj;
    }

    private void after() {
        System.out.println("谢谢惠顾，欢迎下次光临！");
    }

    private void before() {
        System.out.println("欢迎来到百草园！");
    }
}
