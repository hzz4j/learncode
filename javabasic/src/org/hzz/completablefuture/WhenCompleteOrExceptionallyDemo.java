package org.hzz.completablefuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class WhenCompleteOrExceptionallyDemo {
    public static void main(String[] args) {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "执行有返回结果的任务");
            try {
                Thread.sleep(5000);
                if (new Random().nextInt(10) % 2 == 0) {
                    return 12 / 0;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 12;
        });

        future.whenComplete((s, throwable) -> {
            if (throwable == null) {
                System.out.println("whenComplete->Result: " + s);
            } else {
                System.out.println("whenComplete->Exception: " + throwable.getMessage());
            }
        });

        future.exceptionally(throwable -> {
            System.out.println("exceptionally->Exception: " + throwable.getMessage());
            System.out.println("exceptionally->异常："+throwable.getMessage()+"，返回默认值");
            return null;
        });

        Integer result = future.join();
        System.out.println("Result: " + result);
        System.out.println("main end");
    }
}
