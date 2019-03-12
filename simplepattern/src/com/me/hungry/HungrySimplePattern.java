package com.me.hungry;

import lombok.Data;

/**
 * 饿汉式单例，在初始化类时候生成
 * 构造方法需要私有化
 * 线程安全
 * final指向的引用不可变,但是变量的值可以修改
 */

public class HungrySimplePattern {
    private static final HungrySimplePattern HUNGYSIMPLEPATTERN = new HungrySimplePattern();
    private String name;
    static {
        HUNGYSIMPLEPATTERN.setName("测试Final值是否可以修改");
    }
    private HungrySimplePattern(){}
    public static HungrySimplePattern getInstance(){
        return HUNGYSIMPLEPATTERN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
