package com.me.abstractorfactory;

import com.me.factory.IFruitFactory;

public class AbstractFactoryDefaultImpl extends AbstractFactory {
    @Override
    IFruitFactory getFactory(String factoryName) {
        try {
            Class<?> clazz = Class.forName(factoryName);
            IFruitFactory fruitFactory = (IFruitFactory)clazz.newInstance();
            return fruitFactory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
