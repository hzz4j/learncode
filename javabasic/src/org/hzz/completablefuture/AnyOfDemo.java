package org.hzz.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class AnyOfDemo {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(getSupplier("HuangZhuangzhuang"));

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(
                getSupplier("Q10Viking")
        );

        CompletableFuture.anyOf(future1, future2)
                .thenAccept(result -> System.out.println("任务完成了，结果是：" + result));

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
 * 任务完成了，结果是：HuangZhuangzhuang
 * * 或者
 * 任务完成了，结果是：Q10Viking
 */