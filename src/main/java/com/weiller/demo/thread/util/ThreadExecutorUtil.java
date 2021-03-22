package com.weiller.demo.thread.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadExecutorUtil {
    private static ExecutorService executorService;
    static {
        int aps =  Runtime.getRuntime().availableProcessors()*10;
        executorService = new ThreadPoolExecutor(
                aps / 4,
                  aps,
                10L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }

    public static ExecutorService getExecutorService(){
         return executorService;
    }
}
