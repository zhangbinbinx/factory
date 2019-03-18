package com.me.proxy;


import java.util.ArrayList;
import java.util.List;

public class BaiCaoYuan implements IFruit {
    List<Fruit> fruitList = new ArrayList<Fruit>();
    @Override
    public String getName(Fruit fruit) {
        System.out.println("查询水果名称中......");
        return fruit == null ? "当前对象为空" : fruit.getName();
    }

    @Override
    public String getWeight(Fruit fruit) {
        System.out.println("水果称重中......");
        return fruit == null ? "当前对象为空" : fruit.getWeight();
    }

    @Override
    public String salse(Fruit fruit) {
        System.out.println("开始销售水果");
        String result;
        if(fruitList.size() == 0){
            result = "当前水果已销售完，请及时采购！";
        }
        if(fruitList.remove(fruit)){
            result = "销售成功！";
        }else{
            result = "无复合条件的水果！";
        }
        System.out.println(result);
        return result;
    }

    public List<Fruit> getFruitList() {
        return fruitList;
    }

    public void setFruitList(List<Fruit> fruitList) {
        this.fruitList = fruitList;
    }
}
