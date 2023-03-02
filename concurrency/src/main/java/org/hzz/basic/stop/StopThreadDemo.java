package org.hzz.basic.stop;

public class StopThreadDemo implements Runnable{
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new StopThreadDemo());
        thread.start();
        Thread.sleep(5);
        thread.interrupt();
    }

    @Override
    public void run() {
        int count = 0;
        while(!Thread.currentThread().isInterrupted()
        && count <= 1000){
            System.out.println("count = " + count++);
        }
        System.out.println("线程停止：stop thread");
    }
}
/**output: 可以看到线程终止循环不是因为count >= 1000
 * count = 140
 * count = 141
 * count = 142
 * 线程停止：stop thread
 */