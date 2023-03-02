package org.hzz.basic.sleep;

public class SleepInteruptDemo implements Runnable{

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new SleepInteruptDemo());
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
            }
        }
        System.out.println("线程停止：stop thread");
    }
}
/** 验证Thread.sleep期间会检测到中断并清除中断标志
 * count = 997
 * count = 998
 * count = 999
 * 线程停止：stop thread
 */