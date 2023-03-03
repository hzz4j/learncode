package org.hzz.basic.locksupport;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ParkThread(),"thread-park");
        thread.start();
        Thread.sleep(3000);
        System.out.println("唤醒线程"+thread.getName());
        LockSupport.unpark(thread);
    }

    static class ParkThread implements Runnable{

        @Override
        public void run() {
            System.out.printf("%s 开始执行\n", Thread.currentThread().getName());
            LockSupport.park();
            System.out.printf("%s 执行完成\n", Thread.currentThread().getName());
        }
    }
}
/**
 * thread-park 开始执行
 * 唤醒线程thread-park
 * thread-park 执行完成
 */