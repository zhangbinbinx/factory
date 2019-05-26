package com.me.thread;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ConditionSignalDemo implements Runnable {
    private Lock lock;
    private Condition condition;

    public ConditionSignalDemo(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    public void run() {
        try {
            System.out.println("begin ConditionSignal");
            lock.lock();
            condition.signal();
            System.out.println("end ConditionSignal");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
