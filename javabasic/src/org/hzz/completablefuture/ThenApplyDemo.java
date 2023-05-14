package org.hzz.completablefuture;

import java.util.concurrent.CompletableFuture;

public class ThenApplyDemo {
    public static void main(String[] args) {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int result = 100;
            System.out.println("一阶段：" + result);
            return result;
        }).thenApply(number -> {
            int result = number * 3;
            System.out.println("二阶段：" + result);
            return result;
        });

        System.out.println("最终结果" + future.join());
    }
}
/**
 * 一阶段：100
 * 二阶段：300
 * 最终结果300
 */
