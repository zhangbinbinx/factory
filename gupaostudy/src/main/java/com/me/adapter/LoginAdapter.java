package com.me.adapter;

public interface LoginAdapter {
    boolean support(LoginAdapter loginAdapter);
    String login(String ... args);
}
