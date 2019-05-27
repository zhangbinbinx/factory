1 ConcurrentHashMap1.8中是基于什么机制来保证线程安全性的
通过compareAndSwapInt对应的乐观锁，实现了原子操作来保证线程安全。

2 ConcurrentHashMap通过get方法获取数据的时候，是否需要通过加锁来保证数据的可见性？为什么？
没有加锁，通过getObjectVolatile方法，获取当前可见的元素。
3 ConcurrentHashMap1.7和ConcurrentHashMap1.8有哪些区别？
    1)添加一个类ConcurrentHashMap.KeySetView<K,V>
    2)JDK1.8使用红黑树来优化链表
    3)JDK1.8的摒弃了Segment的概念，而是直接用Node数组+链表+红黑树的数据结构来实现
4 ConcurrentHashMap1.8为什么要引入红黑树？
优化数据存储结构，使用红黑树在数据量大的时候，保证查询的速度更快。