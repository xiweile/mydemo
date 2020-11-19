package com.weiller.demo.utils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadPoolUtil {

    private static final int MAX_CONC = 5000;
    private static final int cpuNums = Runtime.getRuntime().availableProcessors();  //24
    private static final int POOL_SIZE = 1;// 单个CPU线程池大小
    private static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(MAX_CONC);
    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
            cpuNums*POOL_SIZE,
            cpuNums*POOL_SIZE*10,
            30,
            TimeUnit.SECONDS,
            queue, new ThreadFactory() {
        private AtomicLong threadIndex = new AtomicLong(0);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "thread_" + threadIndex.incrementAndGet());
        }
    }); // 线程池主
    
    
    static{
        poolExecutor.prestartAllCoreThreads();
    }
    
    
    public static ThreadPoolExecutor getPoolExecutor(){
    	return poolExecutor;
    }
    
}
