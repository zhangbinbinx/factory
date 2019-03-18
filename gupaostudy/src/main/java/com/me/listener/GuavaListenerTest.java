package com.me.listener;

import com.google.common.eventbus.EventBus;

public class GuavaListenerTest {
    public static void main(String[] args) {
        //监听者和被监听对象需要使用同一个消息总线
        EventBus eventBus = new EventBus();
       /* GuavaListener guavaListener = new GuavaListener();
        eventBus.register(guavaListener);
        eventBus.post("AAA");*/
        Gper gper = Gper.getInstance();

        Question question = new Question();
        question.setUserName("如花");
        question.setContent("观察者设计模式适用于哪些场景？");
        gper.publishQuestion(question,eventBus);
    }
}
