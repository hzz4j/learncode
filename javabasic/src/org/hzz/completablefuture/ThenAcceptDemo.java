package org.hzz.completablefuture;

import java.util.concurrent.CompletableFuture;

public class ThenAcceptDemo {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture.supplyAsync(()->{
            Utils.sleepRandomSeconds();
            return "hello";
        }).thenAccept(result->{
            System.out.println("任务完成了，结果是：" + result);
        });
        Thread.currentThread().join();
    }
}
/**
 * 任务完成了，结果是：hello
 */