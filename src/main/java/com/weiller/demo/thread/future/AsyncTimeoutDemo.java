package com.weiller.demo.thread.future;

import com.weiller.demo.thread.util.ThreadExecutorUtil;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 线程异步超时
 */
public class AsyncTimeoutDemo {

    public static void main(String[] s) {
        //ExecutorService executor = ThreadExecutorUtil.getExecutorService();
        ExecutorService executor1 = ThreadExecutorUtil.getExecutorService();
        for (int i=1;i<=5;i++){
            System.err.println("Start_"+i);

            final int num = i;
            // 返回值为boolean
            executor1.execute (() -> {
                Future<?> future = executor1.submit(new Task("task_" + num));
                try {
                    future.get(30, TimeUnit.SECONDS);
                } catch (TimeoutException e){
                    System.out.println("future["+num+"]时间超时");
                    future.cancel(true);
                } catch (InterruptedException e){
                    System.out.println("future["+num+"]执行中断");
                    future.cancel(true);
                }catch (Exception e) {
                    System.out.println("future["+num+"]执行异常:"+e.getMessage());
                    future.cancel(true);
                } finally {
                   executor1.shutdown();
                }
            });
            System.err.println("end_"+i);
        }

    }

    /**
     *
     */
    static class Task implements Runnable {

        private String name;

        public Task(String name)  {
            this.name = name;
        }

        @Override
        public void run()  {
            System.out.println("["+name+"]执行开始");
            try {
                int timeout = new Random().nextInt(100)*1000  +1;
                System.out.println("["+name+"] timeout "+timeout +"ms");
                Thread.sleep(timeout );
                System.out.println("["+name+"]执行完成");
            }catch (InterruptedException e){
                System.out.println("["+name+"]执行中断");
              //  e.printStackTrace();
            }
        }


    }
}
