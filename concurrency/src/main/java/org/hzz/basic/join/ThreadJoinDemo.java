package org.hzz.basic.join;

public class ThreadJoinDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()->{
            System.out.println("t begin");
            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t end");
        });
        long start = System.currentTimeMillis();
        thread.start();
        // 主线程main等待线程t执行完毕后再继续执行
        thread.join();
        System.out.println("执行时间："+(System.currentTimeMillis() - start));
        System.out.println("Main finished.");
    }
}
/**
 * t begin    // 输出这句后程序等待了一段时间
 * t end
 * 执行时间：5015
 * Main finished.
 */