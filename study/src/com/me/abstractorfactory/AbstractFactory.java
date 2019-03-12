package com.me.abstractorfactory;

import com.me.factory.IFruitFactory;

public abstract class AbstractFactory {
    abstract IFruitFactory getFactory(String factoryName);
}
