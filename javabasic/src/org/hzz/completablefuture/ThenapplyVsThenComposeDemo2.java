package org.hzz.completablefuture;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

public class ThenapplyVsThenComposeDemo2 {
    public static void main(String[] args) {

        // 同一个CompletableFuture类型都是String
        CompletableFuture<String> future1 = CompletableFuture.completedFuture("Hello");
        CompletableFuture<String> result1 = future1.thenApply(param -> param + " World");

        // 两个CompletableFuture类型不一样
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {});
        CompletableFuture<LocalDate> result2 = future2.thenCompose((Void) -> CompletableFuture.completedFuture(LocalDate.now()));
        System.out.println(result1.join());
        System.out.println(result2.join());
    }
}
/**
 * Hello World
 * 2023-05-15
 */