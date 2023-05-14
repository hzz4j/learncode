package org.hzz.completablefuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.function.Supplier;

public class ThenComposeDemo {
    public static void main(String[] args) {
        CompletableFuture<Integer> future = CompletableFuture
                .supplyAsync(new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        int number = new Random().nextInt(30);
                        System.out.println("第一阶段：" + number);
                        return number;
                    }
                })
                .thenCompose(new Function<Integer, CompletionStage<Integer>>() {

                    // CompletableFuture继承了CompletionStage
                    @Override
                    public CompletionStage<Integer> apply(Integer param) {
                        return CompletableFuture.supplyAsync(new Supplier<Integer>() {
                            @Override
                            public Integer get() {
                                int number = param * 2;
                                System.out.println("第二阶段：" + number);
                                return number;
                            }
                        });
                    }
                });

        System.out.println("最终结果：" + future.join());
    }
}
/**
 * 第一阶段：18
 * 第二阶段：36
 * 最终结果：36
 */