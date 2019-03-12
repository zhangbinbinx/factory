package com.me.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RegistryPattern {
    private static Map<String,Object> registryPatternMap = new ConcurrentHashMap<String, Object>();
    private RegistryPattern(){}
    public static Object getInstance(String className){
        Object obj;
        synchronized (registryPatternMap){
            obj = registryPatternMap.get(className);
            if(obj == null){
                try {
                    Class<?>clazz = Class.forName(className);
                    obj = clazz.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                registryPatternMap.put(className,obj);
            }
        }
       return obj;
    }
}
