package org.hzz.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class AsyncDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture.runAsync(()->{
            System.out.println(Thread.currentThread().getName()+"执行没有返回结果的任务");
        });

        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName()+"执行有返回结果的任务");
            try {
                TimeUnit.SECONDS.sleep(5);
                return "hello completableFuture";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // 获取结果
//        String s = future.get();
         String s = future.join();
        System.out.println("Result: "+s);
        System.out.println("main end");
    }
}
/**
 * ForkJoinPool.commonPool-worker-9执行没有返回结果的任务
 * ForkJoinPool.commonPool-worker-9执行有返回结果的任务
 * Result: hello completableFuture
 * main end
 */