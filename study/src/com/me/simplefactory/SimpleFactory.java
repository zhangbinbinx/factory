package com.me.simplefactory;

import com.me.model.IFruit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 简单工厂，面向用户
 * 职责：提供水果的出售
 */
public class SimpleFactory {

    public static IFruit getInstance(String className,String weight){
        IFruit fruit = null;
        try {
            Class<?> clazz = Class.forName(className);
            Constructor constructor = clazz.getDeclaredConstructor(null);
            constructor.setAccessible(true);
            fruit = (IFruit) constructor.newInstance(null);

            Method method = clazz.getMethod("setWeight",String.class);
            method.invoke(fruit,weight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fruit;
    }
}
