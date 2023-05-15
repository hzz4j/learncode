package org.hzz.completablefuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ApplyToEitherDemo {
    public static void main(String[] args) throws InterruptedException {

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(
                integerSupplier("future1")
        );

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(
                integerSupplier("future2")
        );

        CompletableFuture<Integer> result = future1.applyToEither(future2, i -> {
            System.out.println("最快的结果: " + i);
            return i * 2;
        });

        System.out.println("最终结果：" + result.join());
        Thread.currentThread().join();
    }

    private static Supplier<Integer> integerSupplier(final String name){
        final Random random = new Random();
        return new Supplier<Integer>() {
            @Override
            public Integer get() {
                System.out.println(name + " start");
                int seconds = random.nextInt(10);
                Utils.sleepSeconds(seconds);
                System.out.println(name + " end, result = "+ seconds);
                return seconds;
            }
        };
    }

}
