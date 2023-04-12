package org.hzz.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureDemo {
    private static final ExecutorService executor = Executors.newFixedThreadPool(1);
    // 创建Future对象：您可以使用ExecutorService提交一个任务以进行异步执行，并获取代表任务结果的Future对象


    public static void main(String[] args) {
        // 创建Future对象：您可以使用ExecutorService提交一个任务以进行异步执行，并获取代表任务结果的Future对象
        Future<String> future = executor.submit(() -> {

            return "Hello";
        });
        // 从Future对象中获取任务结果
        // if(future.isDone())
        if (true) {
            try {
                String result = future.get(); // 获取任务的结果
                System.out.println(result);
            } catch (InterruptedException | ExecutionException e) {
                // 处理在获取结果时可能发生的异常
                e.printStackTrace();
            }
        }else{
            System.out.println("任务未完成");
        }
        executor.shutdown();
    }
}
