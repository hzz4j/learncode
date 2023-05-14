package org.hzz.completablefuture;

import java.util.concurrent.CompletableFuture;

public class ThenapplyVsThenComposeDemo {
    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<String> result1 = future.thenApply(param -> param + " World");
        CompletableFuture<String> result2 = future
                .thenCompose(param -> CompletableFuture.supplyAsync(() -> param + " World"));

        System.out.println(future.join());
        System.out.println(result1.join());
        System.out.println(result2.join());
        System.out.println("===========================================");
        System.out.println("future == result1: " + (future == result1));
        System.out.println("future == result2: " + (future == result2));
    }
}
/**
 * Hello
 * Hello World
 * Hello World
 * ===========================================
 * future == result1: false
 * future == result2: false
 */