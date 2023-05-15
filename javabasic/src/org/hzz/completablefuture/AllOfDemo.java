package org.hzz.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class AllOfDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(getSupplier("HuangZhuangzhuang"));

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(
                getSupplier("Q10Viking")
        );

        CompletableFuture<Void> combineFuture = CompletableFuture.allOf(future1, future2);
        combineFuture.thenAccept(result -> System.out.println("任务完成了，结果是：" + result));

        System.out.println(combineFuture.get());
        Thread.currentThread().join();
    }

    private static Supplier<String> getSupplier(final String msg){
        return ()->{
            Utils.sleepRandomSeconds();
            System.out.println(msg + " end");
            return msg;
        };
    }
}
/**
 * Q10Viking end
 * HuangZhuangzhuang end
 * 任务完成了，结果是：null
 * null
 */