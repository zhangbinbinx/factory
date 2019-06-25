1 为什么要使用线程池？
 减少线程创建及销毁造成的开销，适用场景为连接总数固定在一个区间，请求切换频繁的场景
2 Executors提供的四种线程池:newSingleThreadExecutor,newFixedThreadPool,newCachedThreadPool,newScheduledThreadPool ，请说出他们的区别以及应用场景
    newSingleThreadExecutor:单线程
    newFixedThreadPool 指定线程大小及核心池大小
    newCachedThreadPool:创建多个线程，线程60秒不使用会被销毁
    newScheduledThreadPool 创建调度对应的线程，定时启动执行任务
    
3 线程池有哪几种工作队列？
  SynchronousQueue ： 同步队列
  LinkedBlockingQueue：链表队列

4 线程池默认的拒绝策略有哪些
    CallerRunsPolicy ： 直接运行run方法
    AbortPolicy：抛出异常信息
    DiscardPolicy：不做任何处理
    DiscardOldestPolicy：当前队列poll后执行该线程
5 如何理解有界队列和无界队列

有界队列：有固定的大小

无界队列:无固定大小

6 线程池是如何实现线程回收的？ 以及核心线程能不能被回收？

根据配置的心跳时间，超时后回收

根据类型决定会不会回收核心线程

7 FutureTask是什么

Future是一个接口，FutureTask是Future的一个实现类，并实现了Runnable，因此FutureTask可以传递到线程对象，执行线程并获取返回结果

8 Thread.sleep(0)的作用是什么

触发操作系统进行资源抢占

9 如果提交任务时，线程池队列已满，这时会发生什么
根据配置的拒绝策略执行
10 如果一个线程池中还有任务没有执行完成，这个时候是否允许被外部中断？

shutDown() ：执行完成此次任务后退出

shutdownNow()：设置状态为Stop并尝试停止线程