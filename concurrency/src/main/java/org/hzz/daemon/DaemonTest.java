package org.hzz.daemon;

public class DaemonTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.printf("%s 开始执行\n",Thread.currentThread().getName());
        DaemonThread daemonThread = new DaemonThread();
        daemonThread.start();
        Thread.sleep(5000);
        System.out.printf("%s 运行结束\n",Thread.currentThread().getName());
    }
}
/**
 * main 开始执行
 * 我是守护线程，每个一秒发送一次心跳，我依赖与主线程，主线程结束我就结束
 * 发送心跳1次
 * 发送心跳2次
 * 发送心跳3次
 * 发送心跳4次
 * 发送心跳5次
 * main 运行结束
 */