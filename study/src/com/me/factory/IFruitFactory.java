package com.me.factory;

import com.me.model.IFruit;

import java.util.List;

/**
 * 工厂类，面向用户
 * 职责： 提供水果的生产和销售
 */
public interface IFruitFactory {
    IFruit create(String className,String weight);
    void sales(IFruit fruit);
    List<IFruit> getFruitList();
}
