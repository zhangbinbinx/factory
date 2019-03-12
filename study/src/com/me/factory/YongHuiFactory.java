package com.me.factory;

import com.me.model.IFruit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class YongHuiFactory implements IFruitFactory {
    List<IFruit> fruitList = new ArrayList<>();
    @Override
    public IFruit create(String className, String weight) {
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
        System.out.println("永辉生活超市采购了一个苹果！");
        fruitList.add(fruit);
        return fruit;
    }

    @Override
    public void sales(IFruit fruit) {
        fruitList.remove(fruit);
        System.out.println("永辉生活超市成功出售一个苹果！");
    }

    @Override
    public List<IFruit> getFruitList() {
        return fruitList;
    }
}
