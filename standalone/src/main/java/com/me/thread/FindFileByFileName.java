package com.me.thread;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FindFileByFileName {
    public static Map<String, String> filesMap = null;
    private static int count = 0;
    // private static Map<String,Integer> threadCountMap = new HashMap<>();
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(15, 20, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
   // private static final Object object = new Object();

    public static Map<String, String> findFileByFileNameOld(Long beginDate, String fileName, String dir) {
        // threadCountMap.put("threadCount",0);
        try {
            //synchronized (object) {
                filesMap = new HashMap<String, String>();
                if (dir == null) {//查找所有盘符

                } else {
                    findFileByFileName(beginDate, fileName, dir, filesMap);
                }
           // }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return filesMap;
    }

    private static Map<String, String> findFileByFileName(Long beginDate, String fileName, String dir, Map<String, String> filesMap) {
        File dirFile = new File(dir);
        System.out.println("当前正在" + dir + "目录中查找文件！");
        System.out.println("当前已扫描文件个数为" + count + "个！");
        if (null != dirFile && dirFile.isDirectory()) {
            for (File file : dirFile.listFiles()) {

              /*  executor.getThreadFactory().newThread(() -> {
                    findFileByFileNameOld(fileName, file.getAbsolutePath(), filesMap);
                }).start();*/
              /* executor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        findFileByFileNameOld(fileName, file.getAbsolutePath(), filesMap);
                                    }
                                });*/
                System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
                        executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
                executor.execute(()->{
                    findFileByFileNameOld(fileName, file.getAbsolutePath(), filesMap);
                });
                //synchronized (FindFileByFileName.class) {
                // int threadCount = threadCountMap.get("threadCount");
                // if (threadCount < 20) {

                     /*   synchronized (threadCountMap){
                            threadCount++;
                            threadCountMap.put("threadCount",threadCount);
                        }*/


                //()->{
                //                            threadCount ++;
                //                            findFileByFileNameOld(fileName,file.getAbsolutePath(),filesMap);
                //                            threadCount --;
                //                        }
                //FutureTask futureTask = new FutureTask(new MyCallable());
                        /*new Thread(new Runnable() {
                            @Override
                            public void run() {
                                *//*synchronized (FindFileByFileName.class){
                                    threadCount++;
                                }*//*
                                findFileByFileNameOld(fileName, file.getAbsolutePath(), filesMap);
                                synchronized (threadCountMap){
                                    int threadCount = threadCountMap.get("threadCount");
                                    threadCountMap.put("threadCount",threadCount--);
                                    //threadCount--;
                                }
                            }
                        }, "Thread-A" + threadCount).start();*/


                // }else{
                //  findFileByFileNameOld(fileName, file.getAbsolutePath(), filesMap);
                // }


                //  }

            }
        } else {
            String name = dirFile.getAbsolutePath();
            System.out.println("当前扫描的文件为" + name);
            count++;
            if (name.indexOf(fileName) > -1) {
                filesMap.put(name, fileName);
            }
        }


        return filesMap;
    }

    private static Map<String, String> findFileByFileNameOld(String fileName, String dir, Map<String, String> filesMap) {
        File dirFile = new File(dir);
        System.out.println("当前正在" + dir + "目录中查找文件！");
        System.out.println("当前已扫描文件个数为" + count + "个！");
        if (null != dirFile && dirFile.isDirectory()) {
            for (File file : dirFile.listFiles()) {
                findFileByFileNameOld(fileName, file.getAbsolutePath(), filesMap);
            }

        } else {
            String name = dirFile.getAbsolutePath();
            System.out.println("当前扫描的文件为" + name);
            count++;
            if (dirFile.getName().indexOf(fileName) > -1) {
                filesMap.put(name, fileName);
            }
        }
        return filesMap;
    }

    public static void main(String[] args) {
        String fileName = "需求文档";
        String dir = "G:\\";
        Long beginDate = Calendar.getInstance().getTimeInMillis();
        findFileByFileNameOld(beginDate, fileName, dir);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (executor.getActiveCount() > 0) {
            try {
                System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" + executor.getQueue().size() + "，已执行玩别的任务数目：" + executor.getCompletedTaskCount());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Map.Entry<String, String> e : filesMap.entrySet()) {
            System.out.println("当前要查找的文件名称是：" + fileName + ",包含此文件的路径为" + e.getKey());
        }
        System.out.println("本次查询用时为：" + (Calendar.getInstance().getTimeInMillis() - beginDate) / 1000 + "秒！");
        executor.shutdown();

    }
}
