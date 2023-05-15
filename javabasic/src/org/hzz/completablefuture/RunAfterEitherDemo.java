package org.hzz.completablefuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class RunAfterEitherDemo {
    public static void main(String[] args) throws InterruptedException {

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(
                integerSupplier("future1")
        );

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(
                integerSupplier("future2")
        );

       future1.runAfterEither(future2, () -> {
           System.out.println("已经有一个任务完成了");
        }); // 并关心返回结果

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
/**
 * future2 start
 * future1 start
 * future2 end, result = 5
 * 已经有一个任务完成了
 * future1 end, result = 7
 */
