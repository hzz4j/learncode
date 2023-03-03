package org.hzz.basic.parentvschild;

public class ParentContinueAfterChildOver {
    public static void main(String[] args) throws InterruptedException {
        System.out.printf("%s开始\n",Thread.currentThread().getName());
        Thread sonThread = new Thread(() -> {
            System.out.printf("%s开始\n", Thread.currentThread().getName());
            try {
                System.out.printf("%s HI :)\n", Thread.currentThread().getName());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        sonThread.start();
        sonThread.join();
        System.out.printf("%s结束\n",Thread.currentThread().getName());
    }
}
/**
 * main开始
 * Thread-0开始
 * Thread-0 HI :)  // 这句话输出之后等待了5s
 * main结束
 */