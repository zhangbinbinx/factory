package com.me.adapter;

public class LoginForWechartAdapter implements LoginAdapter {
    @Override
    public boolean support(LoginAdapter loginAdapter) {
        return loginAdapter instanceof LoginForWechartAdapter;
    }

    @Override
    public String login(String... args) {
        String wechart = args[0];
        System.out.println("微信号为" + wechart + "的用户正在登录");
        return null;
    }
}
