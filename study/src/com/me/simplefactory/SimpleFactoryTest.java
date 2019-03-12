package com.me.simplefactory;

import com.me.model.IFruit;

public class SimpleFactoryTest {
    public static void main(String[] args) {
        IFruit fruit = SimpleFactory.getInstance("com.me.model.Apple","0.5千克的大苹果！");
        System.out.println("当前水果为：" + fruit.getName() + "他的重量是：" + fruit.getWeight());
        fruit = SimpleFactory.getInstance("com.me.model.Orange","0.1千克的小橘子！");
        System.out.println("当前水果为：" + fruit.getName() + "他的重量是：" + fruit.getWeight());
    }
}
