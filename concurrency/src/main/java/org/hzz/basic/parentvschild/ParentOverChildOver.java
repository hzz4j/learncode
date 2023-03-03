package org.hzz.basic.parentvschild;

public class ParentOverChildOver {
    public static void main(String[] args) {
        System.out.printf("%s开始\n",Thread.currentThread().getName());
        Thread sonThread = new Thread(() -> {
            System.out.printf("%s开始\n", Thread.currentThread().getName());
            while (true) {
                try {
                    System.out.printf("%s HI :)\n", Thread.currentThread().getName());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // 设置为守护线程
        sonThread.setDaemon(true);
        sonThread.start();
        System.out.printf("%s结束\n",Thread.currentThread().getName());
    }
}
/**
 * main开始
 * main结束
 * Thread-0开始
 * Thread-0 HI :)   // 运行完这句就结束了
 */