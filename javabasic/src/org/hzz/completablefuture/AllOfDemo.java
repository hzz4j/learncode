package org.hzz.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AllOfDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(getSupplier("Q10Viking"));

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(
                getSupplier("is")
        );

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(
                getSupplier("a great java programmer.")
        );

        System.out.println("all of start");
        CompletableFuture<Void> combineFuture = CompletableFuture.allOf(future1, future2);
        System.out.println("all of end");

        System.out.println(combineFuture.get());
        System.out.println("future1 isDone = "+future1.isDone());
        System.out.println("future2 isDone = "+future2.isDone());
        System.out.println("future3 isDone = "+future3.isDone());

        String result = Stream.of(future1, future2, future3)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" "));
        System.out.println(result);
    }

    private static Supplier<String> getSupplier(final String msg){
        return ()->{
            Utils.sleepRandomSeconds();
            return msg;
        };
    }
}
/**
 * all of start
 * all of end
 * null
 * future1 isDone = true
 * future2 isDone = true
 * future3 isDone = true
 * Q10Viking is a great java programmer.
 */