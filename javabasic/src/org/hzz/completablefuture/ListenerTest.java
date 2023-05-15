package org.hzz.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ListenerTest {

    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
           sleepSeconds(5);
            System.out.println("开始执行任务");
            return 8;
        });

        completableFuture.whenComplete((result, throwable) -> {
            System.out.println("监听器1,执行，result=" + result);
        });

        completableFuture.whenComplete((result, throwable) -> {
            System.out.println("监听器2,执行，hello Q10Viking");
        });

        Thread.currentThread().join();
    }


    public static void sleepSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
/**
 * 开始执行任务
 * 监听器2,执行，hello Q10Viking
 * 监听器1,执行，result=8
 */