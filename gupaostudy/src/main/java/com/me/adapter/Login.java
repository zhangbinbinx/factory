package com.me.adapter;

public class Login {
    public String login(String username,String pwd){
        System.out.println("原有的登录方法");
        return null;
    }
    public String register(String name,String pwd){
        System.out.println("原有的注册方法");
        //插入信息到数据库
        //调用登录方法
        return login(name,pwd);
    }
}
