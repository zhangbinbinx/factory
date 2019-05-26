package com.me.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ConditionWaitDemo implements Runnable {
    private Lock lock;
    private Condition condition;

    public ConditionWaitDemo(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    public void run() {

        try {
            System.out.println("begin ConditionWait");
            lock.lock();
            condition.await();
            System.out.println("end ConditionWait");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
}
