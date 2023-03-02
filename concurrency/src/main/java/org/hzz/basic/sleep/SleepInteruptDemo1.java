package org.hzz.basic.sleep;

public class SleepInteruptDemo1 implements Runnable{

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new SleepInteruptDemo1());
        thread.start();
        Thread.sleep(5);
        thread.interrupt();
    }
    @Override
    public void run() {
        int count = 0;

        while(!Thread.currentThread().isInterrupted()
            && count < 1000){
            System.out.println("count = " + count++);
            try{
                // 线程执行任务期间有休眠需求
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("检测到中断异常");
                // 由于sleep过程中出现了中断异常中断标志被清除了
                // 所以需要重新打中断标志
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("线程停止：stop thread");
    }
}
/** 验证Thread.sleep期间会检测到中断并清除中断标志
 * count = 0
 * count = 1
 * count = 2
 * count = 3
 * 检测到中断异常
 * 线程停止：stop thread
 */