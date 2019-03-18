package com.me.strategy;

public abstract class BookSite {
    public abstract String getName();

    public void bookRead(String bookSite){

            System.out.println("正在" + getName() + "网站学习中！");

    }
}
