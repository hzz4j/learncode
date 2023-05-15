package org.hzz.completablefuture;

import java.util.concurrent.CompletableFuture;

public class ThenRunDemo {
    public static void main(String[] args) {
        CompletableFuture.completedFuture("Love Java Programming")
                .thenRun(()-> System.out.println("I am running...don't care about the result"));
    }
}
/**
 * I am running...don't care about the result
 */