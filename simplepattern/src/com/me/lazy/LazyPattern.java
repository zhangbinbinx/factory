package com.me.lazy;

/**
 * 懒加载模式
 * 双重锁机制，解决访问时的线程安全问题
 */
public class LazyPattern {
    private static LazyPattern lazyPattern;
    private LazyPattern(){}
    public static LazyPattern getInstance(){
        if(lazyPattern == null){
            synchronized (LazyPattern.class){
                if(lazyPattern == null){
                    lazyPattern = new LazyPattern();
                }
            }
        }
        return lazyPattern;
    }

}
