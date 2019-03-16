package com.me.proxy;

public class ProxyTest {
    public static void main(String[] args) {
        /*Fruit fruit = new Fruit();
        fruit.setName("苹果");
        fruit.setWeight("0.2千克");
        BaiCaoYuan baiCaoYuan = new BaiCaoYuan();
        baiCaoYuan.getFruitList().add(fruit);
        fruit = new Fruit();
        fruit.setName("苹果");
        fruit.setWeight("0.5千克");
        baiCaoYuan.getFruitList().add(fruit);
        String result = baiCaoYuan.salse(fruit).toString();
        System.out.println(result);*/
        Fruit fruit = new Fruit();
        fruit.setName("苹果");
        fruit.setWeight("0.2千克");
        IFruit proxy = (IFruit)new FruitProxy().getInstance(new BaiCaoYuan());
        //proxy.getName(fruit);
        String result = proxy.salse(fruit);
        //System.out.println(result);
    }
}
