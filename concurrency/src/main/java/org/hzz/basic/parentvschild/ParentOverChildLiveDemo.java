package org.hzz.basic.parentvschild;

public class ParentOverChildLiveDemo {
    public static void main(String[] args) {
        System.out.printf("%s开始\n",Thread.currentThread().getName());
        new Thread(()->{
            System.out.printf("%s开始\n",Thread.currentThread().getName());
            while(true){
                try {
                    System.out.printf("%s HI :)\n",Thread.currentThread().getName());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        System.out.printf("%s结束\n",Thread.currentThread().getName());
    }
}
/**
 * main开始
 * main结束
 * Thread-0开始
 * Thread-0 HI :)
 * Thread-0 HI :)
 * Thread-0 HI :)
 * ...
 */