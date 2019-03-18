package com.me.adapter;

public class LoginTest {
    public static void main(String[] args) {
        LoginAdapter loginAdapter = new LoginForQQAdapter();
        loginAdapter.login("小明","13213333233");
        loginAdapter = new LoginForWechartAdapter();
        loginAdapter.login("WeiXiao");
    }
}
