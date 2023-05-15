package org.hzz.completablefuture;

import java.util.concurrent.CompletableFuture;

public class CompleteFutureDemo {
    public static void main(String[] args) {
        CompletableFuture.completedFuture("Love Java")
                .thenAccept(System.out::println);
    }
}
/**
 * Love Java
 */
