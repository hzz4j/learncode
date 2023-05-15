package org.hzz.completablefuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ThenCombineDemo {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture
                .supplyAsync(new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        int number = new Random().nextInt(10);
                        System.out.println("第一阶段：" + number);
                        return number;
                    }
                });
        CompletableFuture<Integer> future2 = CompletableFuture
                .supplyAsync(new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        int number = new Random().nextInt(10);
                        System.out.println("第二阶段：" + number);
                        return number;
                    }
                });
        CompletableFuture<Integer> result = future1
                .thenCombine(future2, new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer x, Integer y) {
                        return x + y;
                    }
                });

        System.out.println("最终结果：" + result.join());
        Thread.currentThread().join();
    }
}
/**
 * 第一阶段：6
 * 第二阶段：3
 * 最终结果：9
 */