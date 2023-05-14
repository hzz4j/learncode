package org.hzz.completablefuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class WhenCompleteOrExceptionallyDemo {
    public static void main(String[] args) {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "执行有返回结果的任务");
            try {
                Thread.sleep(1000);
                int i = new Random().nextInt(10);
                System.out.println("i = " + i);
                if (i % 2 == 0) {
                    return 12 / 0;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 12;
        });

        // 相当于监听器，当future执行完毕后，会回调whenComplete方法
        CompletableFuture<Integer> whenComplete = future.whenComplete((s, throwable) -> {
            if (throwable == null) {
                System.out.println(Thread.currentThread().getName() + ": whenComplete->Result: " + s);
            } else {
                System.out.println(Thread.currentThread().getName() + ": whenComplete->Exception: " + throwable.getMessage());
            }
        });

        // 相当于监听器，当future执行过程抛出异常，会回调exceptionally方法
        CompletableFuture<Integer> exceptionally = future.exceptionally(throwable -> {
            System.out.println(Thread.currentThread().getName() + ": exceptionally->Exception: " + throwable.getMessage());
            System.out.println(Thread.currentThread().getName() + ": exceptionally->异常：" + throwable.getMessage() + "，返回默认值");
            return -1;
        });

        // 如果使用join()方法，会抛出runtime异常，不受检查
        // 如果不catch会导致main线程退出
        // Integer result = future.join();
        // 所以使用get方法比较好

        Integer result = null;
        try {
            Thread.sleep(3000);
            result = future.get();  // 获取不到异常后的返回值，只能获取到异常
            System.out.println("Result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("main->Exception: " + e.getMessage());
        }

        try{
            System.out.println("whenComplete: " + whenComplete.get()); // 和上面一样
        }catch (InterruptedException | ExecutionException e) {
            System.out.println("main->Exception: " + e.getMessage());
        }

        try{
            System.out.println("exceptionally: " + exceptionally.get()); // 获取到异常后的返回值
        }catch (InterruptedException | ExecutionException e) {
            System.out.println("main->Exception: " + e.getMessage());
        }
        System.out.println("main end");
    }
}
