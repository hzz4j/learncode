package org.hzz.basic.interrupt;

public class ThreadInterruptDemo {
    static int i = 0;
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            while(true){
                i++;
                System.out.printf("i = %d\n",i);
                // isInterrupted不会清除中断标志位
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("检测到中断标志");
                }
                if(i == 10){
                    System.out.println("检测到 i = 10 线程t1退出循环");
                    break;
                }
            }
        });

        t1.start();
        t1.interrupt();
    }
}
/**
 * i = 1
 * 检测到中断标志
 * i = 2
 * 检测到中断标志
 * i = 3
 * 检测到中断标志
 * i = 4
 * 检测到中断标志
 * i = 5
 * 检测到中断标志
 * i = 6
 * 检测到中断标志
 * i = 7
 * 检测到中断标志
 * i = 8
 * 检测到中断标志
 * i = 9
 * 检测到中断标志
 * i = 10
 * 检测到中断标志
 * 检测到 i = 10 线程t1退出循环
 */