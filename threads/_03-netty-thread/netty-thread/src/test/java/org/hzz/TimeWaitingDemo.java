package org.hzz;

import java.util.Scanner;

public class TimeWaitingDemo {
    Object monitor = new Object();
    Scanner scanner = new Scanner(System.in);
    volatile String result;

    public static void main(String[] args) {
        TimeWaitingDemo app = new TimeWaitingDemo();
        new Thread(app::input).start();
        app.start();
    }

    private void start() {
        try {
            synchronized (this) {
                System.out.println(Thread.currentThread().getName()+"获得锁,等待输入");
                wait(1000 * 1000 * 1000);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("被唤醒,result: "+result);
    }

    private void input() {
        synchronized (this){
            System.out.println(Thread.currentThread().getName()+"获得锁,请输入");
            result = scanner.nextLine();
            notifyAll();
        }
    }
}
/**
 * main获得锁,等待输入
 * Thread-0获得锁,请输入
 * 你好
 * 被唤醒,result: 你好
 */