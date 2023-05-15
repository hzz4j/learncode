package org.hzz.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ThenAcceptBothDemo {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(getSupplier("HuangZhuangzhuang"));

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(
                getSupplier("call")
        );

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(
                getSupplier("Q10Viking")
        );

        future1.thenAcceptBoth(future2, (result1, result2) -> {
                    System.out.println("future1 isDone = " + future1.isDone());
                    System.out.println("future2 isDone = " + future2.isDone());
                    System.out.println("任务完成了，结果是：" + result1 + " " + result2);
        });
//        }).thenAcceptBoth(future3, (result1, result2) -> {
//            System.out.println("future3 isDone = " + future3.isDone());
//            System.out.println("任务完成了，结果是：" + result1 + " " + result2);
//        });
        Thread.currentThread().join();
    }

    private static Supplier<String> getSupplier(final String msg){
        return ()->{
            Utils.sleepRandomSeconds();
            return msg;
        };
    }
}
/**
 * future1 isDone = true
 * future2 isDone = true
 * 任务完成了，结果是：HuangZhuangzhuang call
 */