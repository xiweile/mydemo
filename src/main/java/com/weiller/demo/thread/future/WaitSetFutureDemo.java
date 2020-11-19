package com.weiller.demo.thread.future;

import com.weiller.demo.utils.ThreadPoolUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 模拟等待消息返回，场景如：单项网络通道的消息传输
 */
public class WaitSetFutureDemo {

    public static ThreadPoolExecutor executor1 = ThreadPoolUtil.getPoolExecutor();

    /**
     * 记录每个messageId对应的WaitFuture，用于回填返回结果
     */
    private static Map<String,WaitFuture<String>> futureTasks = new HashMap<String,WaitFuture<String>>();

    public static class WaitFuture<V> extends FutureTask<V> {

        public WaitFuture() {
            super(new Callable<V>() {

                @Override
                public V call() throws Exception {
                    throw new IllegalStateException("this should never be called");
                }
            });

        }

        public void setResult(V v) {
            set(v);
        }

    }


    /**
     * 发起请求
     * @param msgId 消息ID
     * @return 响应消息
     */
    private static String requestMsg(String msgId){
        WaitFuture<String> waitFuture = new WaitFuture<>();
        futureTasks.put(msgId, waitFuture);
        String responseMsg = null;
        try {
            //此处会让本线程等待，直到ft有结果返回或者超时
            //当监听到返回结果后会填充结果到本ft
            responseMsg  = waitFuture.get(5000, TimeUnit.MILLISECONDS);//等待5秒
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
          System.out.println("请求"+msgId+":等待返回结果时出错！" );
         // e.printStackTrace();
        }
        futureTasks.remove(msgId);
        return responseMsg;
    }

    private static void listeningMsg(String msgId){
        WaitFuture<String> waitFuture = futureTasks.get(msgId);
        waitFuture.setResult("我是"+msgId+"的返回消息");
    }

    public static void main(String[] args) throws InterruptedException {
        // 模拟发送请求
        for (int i=1;i<=10;i++){
            String msgId = String.valueOf(i);
            System.out.println("发起请求："+msgId);
            executor1.submit(()->{
                String requestMsg = requestMsg(msgId);
                System.out.println("响应"+msgId+"："+requestMsg);
            });
        }

        System.out.println("********************");
        // 模拟响应请求
        for (int i=1;i<=10;i++){
            String msgId = String.valueOf(i);
            System.out.println("发起响应："+msgId);
            Thread.sleep(new Random().nextInt(5)*200);//模拟响应不同的时间
            listeningMsg( msgId);
        }
    }

}
