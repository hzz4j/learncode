package org.hzz.completablefuture;

import java.util.concurrent.CompletableFuture;

public class TeaDemo {
    public static void main(String[] args) {
        //任务1：洗水壶->烧开水
        CompletableFuture<Void> futureTask1 = CompletableFuture.runAsync(() -> {
            System.out.println("T1:洗水壶...");
            Utils.sleepSeconds(1);
        }).thenRun(() -> {
            System.out.println("T1:烧开水...");
            Utils.sleepSeconds(15);
        });
        //任务2：洗茶壶->洗茶杯->拿茶叶
        CompletableFuture<String> futureTask2 = CompletableFuture.runAsync(() -> {
            System.out.println("T2:洗茶壶...");
            Utils.sleepSeconds(1);
        }).thenRun(() -> {
            System.out.println("T2:洗茶杯...");
            Utils.sleepSeconds(2);
        }).thenCompose((Void) -> CompletableFuture.supplyAsync(() -> { // 返回一个结果
            System.out.println("T2:拿茶叶...");
            Utils.sleepSeconds(1);
            return "龙井";
        }));

        //任务3：任务1和任务2完成后执行：泡茶
        futureTask1.thenCombine(futureTask2, (Void, tf) -> {
            System.out.println("T3:拿到茶叶:" + tf);
            System.out.println("T3:泡茶...");
            System.out.println("T3: 上茶...");
            return tf;
        }).thenAccept(result -> {
            System.out.println("Q10Viking 喝茶..." + result);
        });

        // 等待任务3执行完成
        futureTask1.join();
    }
}
