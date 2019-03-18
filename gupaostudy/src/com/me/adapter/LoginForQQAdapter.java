package com.me.adapter;

public class LoginForQQAdapter implements LoginAdapter {
    @Override
    public boolean support(LoginAdapter loginAdapter) {
        return loginAdapter instanceof LoginForQQAdapter;
    }

    @Override
    public String login(String ...str) {
        String qq = str[0];
        String mobile = str[1];
        System.out.println("当前登录的qq号码为" + qq + ",当前用户的手机号为" + mobile + "。");
        return null;
    }
}
