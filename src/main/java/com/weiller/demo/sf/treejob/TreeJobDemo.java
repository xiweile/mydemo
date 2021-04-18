package com.weiller.demo.sf.treejob;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.*;
import com.weiller.demo.utils.ThreadPoolUtil;

import java.util.*;
import java.util.concurrent.*;

/**
 * 树形任务执行调度案例
 */
public class TreeJobDemo {

    // 执行失败的节点列表
    private static Set<TNode>  failureNodes = new CopyOnWriteArraySet<>();

    // 执行完成的节点列表
    private static Set<TNode> finishedNodes = new CopyOnWriteArraySet<>();

    // 执行中的节点列表
    private static volatile Set<TNode> runningNodes = new CopyOnWriteArraySet<>();

    // 等候执行的节点队列
    private static volatile LinkedBlockingDeque<TNode> waitingNodes = new LinkedBlockingDeque<>();

    // 叶子节点列表
    private static Set<TNode> leafNodes = new CopyOnWriteArraySet<>();

    // 节点与父级节点关系对照
    private static Map<String, Set<TNode>> nodeParentsMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        // 查找rootId节点的所有子孙节点
        String data = "{\"id\":\"1\",\"title\":\"节点1\",\"childrens\":[{\"id\":\"11\",\"title\":\"节点11\",\"childrens\":[{\"id\":\"111\",\"title\":\"节点111\",\"childrens\":[]},{\"id\":\"112\",\"title\":\"节点112\",\"childrens\":[]}]},{\"id\":\"12\",\"title\":\"节点12\",\"childrens\":[{\"id\":\"111\",\"title\":\"节点111\",\"childrens\":[]},{\"id\":\"121\",\"title\":\"节点121\",\"childrens\":[{\"id\":\"1311\",\"title\":\"节点1311\",\"childrens\":[]}]}]},{\"id\":\"13\",\"title\":\"节点13\",\"childrens\":[{\"id\":\"131\",\"title\":\"节点131\",\"childrens\":[{\"id\":\"1311\",\"title\":\"节点1311\",\"childrens\":[]}]}]}]}";

        TNode rootNode = JSON.parseObject(data, TNode.class);
        // 1.节点分析
        analyseNode(rootNode, "1");

        System.out.println("叶子节点：");
        leafNodes.stream().forEach(e-> {System.out.print(e.getId()+",");});
        System.out.println();
       /* System.out.println("节点与父级节点关系对照：");
        nodeParentsMap.entrySet().forEach(e-> {
            System.out.print(e.getKey()+" => ");
            e.getValue().forEach(v->{
                System.out.print(v.getId()+",");
            });
            System.out.println();
        });*/

        //2.启动执行叶子节点
        for (TNode leafNode :leafNodes){
            try {
                waitingNodes.put(leafNode);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        //3.扫描等待队列
        long sT = System.currentTimeMillis();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runNodeFromWaiting();
                if (finishedNodes.contains(rootNode)){
                    timer.cancel();
                    System.out.println("任务完成...总耗时 "+ Math.round((System.currentTimeMillis()-sT)/1000.0) +"s");
                    System.exit(1);
                }

            }
        }, 0,5000);
        System.out.println("任务开始...");
    }

    private static synchronized void runNodeFromWaiting(){
        int p = 5;//并行度
        if(runningNodes.size()>=p){
            System.out.println("执行任务已满，稍等...");
            return;
        }
        System.out.print("当前执行中：[");
        runningNodes.forEach(n->{System.out.print(n.getId()+",");});
        System.out.print("]");
        System.out.print(" 等待中：[");
        waitingNodes.forEach(n->{System.out.print(n.getId()+",");});
        System.out.print("]");
        System.out.print(" 已完成：[");
        finishedNodes.forEach(n->{System.out.print(n.getId()+",");});
        System.out.print("]");
        if (failureNodes.size()>0){
            System.out.print(" 已失败：[");
            failureNodes.forEach(n->{System.out.print(n.getId()+",");});
            System.out.print("]");
        }

        System.out.println();
        while (waitingNodes.size()>0&& runningNodes.size()<p){//添加新的任务
            TNode poll = waitingNodes.poll();
            if (poll==null){
                continue;
            }
            boolean readOk = true;
            for (TNode c:poll.getChildrens()){
                 if (!finishedNodes.contains(c)){//如果其他子集尚未运行完成，则继续向后排队。
                    readOk = false;
                     System.out.println(poll.getId()+" 依赖任务未全部完成，放弃排队等待。");
                 }
             }

             if (readOk){
                 System.out.println("新加入执行："+poll.getId());
                 startRun(poll);
             }
        }
    }


    private static void startRun(TNode node){
        runningNodes.add(node);
        ThreadPoolUtil.getPoolExecutor().submit(new NodeExecutor(node));
    }


    /**
     * 分析节点组织，得到 叶子节点列表 、节点与父级节点关系对照
     * @param tNode
     */
    private static void analyseNode(TNode tNode,String rootId){
        if (tNode.getChildrens()!=null && tNode.getChildrens().size()>0){
            for (TNode cNode:tNode.getChildrens()){
                Set<TNode> tNodeSet = nodeParentsMap.get(cNode.getId());
                if (tNodeSet!=null){
                    tNodeSet.add(tNode);
                }else{
                    tNodeSet = new HashSet<>();
                    tNodeSet.add(tNode);
                    nodeParentsMap.put(cNode.getId(),tNodeSet);
                }
                analyseNode(cNode,tNode.getId());
            }
        }else{
            //叶子节点收集
            leafNodes.add(tNode);
        }

    }

    static class NodeExecutor implements Callable<Boolean>{

        private TNode node;

        public NodeExecutor(TNode node){
            this.node = node;
        }

        @Override
        public Boolean call() throws Exception {
            System.out.println(node.getId()+" 执行开始");
            long startTime = System.currentTimeMillis();
            try {
                //任务逻辑范围...
                int castTime = 1000 * new Random().nextInt(10);
                Thread.sleep(castTime);
                //任务逻辑范围...

                finishedNodes.add(node);
                System.out.println(node.getId()+" 执行成功！耗费 "+ Math.round((System.currentTimeMillis()-startTime)/1000.0) +"s");
                after();
                return true;
            }catch (Throwable e){
                failureNodes.add(node);
                System.out.println(node.getId()+" 执行失败！");
                return false;
            }finally {
                runningNodes.remove(node);
            }
        }

        private void after(){
            Set<TNode> pNodes = nodeParentsMap.get(node.getId());
            if (pNodes!=null){
                pNodes.forEach(n->{
                    if (!waitingNodes.contains(n) ){
                        try {
                            waitingNodes.put(n);
                            System.out.println(n.getId()+" 排队等待");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        System.out.println(n.getId()+" 已经等待中");
                    }
                });
            }
        }
    }
}
