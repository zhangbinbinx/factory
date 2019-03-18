package com.me.listener;

import com.google.common.eventbus.EventBus;

public class Gper {
    private String name = "GPer生态圈";
    private static Gper gper = null;
    private Gper(){}

    public static Gper getInstance(){
        if(null == gper){
            gper = new Gper();
        }
        return gper;
    }

    public String getName() {
        return name;
    }

    public void publishQuestion(Question question, EventBus eventBus){
        String str = question.getUserName() + "在" + this.name + "上提交了一个问题。";
        System.out.println(str);
        GuavaListener guavaListener = new GuavaListener();
        eventBus.register(guavaListener);
        eventBus.post(str);
    }
}
