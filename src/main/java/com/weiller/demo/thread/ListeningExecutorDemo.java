package com.weiller.demo.thread;

import com.google.common.util.concurrent.*;
import com.weiller.demo.utils.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class ListeningExecutorDemo {

    private static final ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(ThreadPoolUtil.getPoolExecutor());

    static final AtomicLong cnt = new AtomicLong();
    static final AtomicLong cntOk = new AtomicLong();
    static final AtomicLong cntError = new AtomicLong();

    static int testCnt = 100;
    public static void main(String[] args) {

        final CountDownLatch countDownLatch = new CountDownLatch(testCnt);
        try {
            for (int i = 1 ; i<= testCnt;i++){
                try {
                    SquareCallable callable = new SquareCallable(i);
                    ListenableFuture<Double> listenableFuture = listeningExecutorService.submit(callable);
                    final int _i = i;
                    cnt.incrementAndGet();
                    Futures.addCallback(listenableFuture, new FutureCallback<Double>() {
                        @Override
                        public void onSuccess(Double a) {
                            log.info(_i+"^2 = "+a);
                            cntOk.incrementAndGet();
                            countDownLatch.countDown();
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            cntError.incrementAndGet();
                            countDownLatch.countDown();
                        }
                    });
                } catch (Exception e) {
                   log.error("任务执行时错误",e);
                }
            }
            log.info("执行剩余:{}", countDownLatch.getCount());
            countDownLatch.await();
            log.info("执行完成，计划总量：{}，完成量：{}，成功：{}，失败：{}", testCnt,cnt,cntOk,cntError);

        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

    }

    /**
     * 计算传入数字的平方值
     */
   static class SquareCallable implements Callable<Double>{

       private double a;

       public SquareCallable(final double a) throws Exception {
           this.a = a;
       }

        @Override
        public Double call() throws Exception {
            if(a%10==0){
                throw new RuntimeException();
            }
            return Math.pow(a,2);
        }


   }
}
