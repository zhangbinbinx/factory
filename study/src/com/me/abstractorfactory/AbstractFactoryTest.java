package com.me.abstractorfactory;

import com.me.factory.BaiCaoYuanFactory;
import com.me.factory.IFruitFactory;
import com.me.factory.YongHuiFactory;
import com.me.model.IFruit;

public class AbstractFactoryTest {
    public static void main(String[] args) {
        AbstractFactory factory = new AbstractFactoryDefaultImpl();
        IFruitFactory fruitFactory = factory.getFactory("com.me.factory.BaiCaoYuanFactory");
        //IFruitFactory fruitFactory = new BaiCaoYuanFactory();
        fruitFactory.create("com.me.model.Apple","0.5千克的大苹果！");
        fruitFactory.create("com.me.model.Apple","0.6千克的大苹果！");
        fruitFactory.create("com.me.model.Apple","0.4千克的大苹果！");
        IFruit fruit = fruitFactory.create("com.me.model.Apple","0.2千克的大苹果！");
        fruitFactory.sales(fruit);
        for (IFruit f : fruitFactory.getFruitList()) {
            System.out.println("当前水果为：" + f.getName() + "他的重量是" + f.getWeight());
        }
        fruitFactory = new YongHuiFactory();
        fruitFactory.create("com.me.model.Orange","0.1千克的橘子！");
        fruitFactory.create("com.me.model.Apple","0.2千克的橘子！");
        fruitFactory.create("com.me.model.Apple","0.3千克的橘子！");
        fruit = fruitFactory.create("com.me.model.Apple","0.4千克的橘子！");
        fruitFactory.sales(fruit);
        for (IFruit f : fruitFactory.getFruitList()) {
            System.out.println("当前水果为：" + f.getName() + "他的重量是" + f.getWeight());
        }
    }
}
