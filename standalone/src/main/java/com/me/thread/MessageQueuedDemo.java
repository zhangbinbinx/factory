package com.me.thread;


import com.mchange.v2.lang.ThreadUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueuedDemo {
    public  static   Map<String,Object> messageQueuedMap = new HashMap<String,Object>();
    public static final int MAX_SIZE = 20;
    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    public static final void putValue(String key,Object object){
        try{
            lock.lock();
            Thread.sleep(200);
            if(messageQueuedMap.size() < MAX_SIZE){

                messageQueuedMap.put(key,object);

            }else{
                System.out.println("当前队列已满！");
                condition.await();
            }
            System.out.println("添加数据成功！当前值为" + object.toString());
            System.out.println(messageQueuedMap.size());
           condition.signal();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public static final Object getValue(String key){
        Object object = null;
        try {
            lock.lock();
            Thread.sleep(1000);
            if(messageQueuedMap.size() == 0){
                System.out.println("当前队列为空！");
                condition.await();
            }
            System.out.println("获取数据" + key);
            object = messageQueuedMap.get(key);
            messageQueuedMap.remove(key);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            condition.signal();
            lock.unlock();
            return object;
        }
    }

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(()->{
            for (int i = 0; i < 50; i++) {
                getValue(i + "");
            }
        }).start();
        new Thread(()->{
            for (int i = 0; i < 50; i++) {
                putValue(i + "","value" + i);
            }
        }).start();
        //ConditionWaitDemo conditionWaitDemo = new ConditionWaitDemo(lock,condition);
       // new Thread(new ConditionWaitDemo(lock,condition)).start();
        //new Thread(new ConditionSignalDemo(lock,condition)).start();
    }
}
