package com.me.mebatis;

import java.lang.reflect.Proxy;
import java.util.ResourceBundle;

public class MyConfiguration {
    public static final  ResourceBundle sqlMappings;
    static {
        sqlMappings = ResourceBundle.getBundle("mesql");
    }
    public <T> T getMapper(Class clazz,SqlSession sqlSession) {
        return (T)Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[]{clazz},new MapperProxy(sqlSession));
    }
}
