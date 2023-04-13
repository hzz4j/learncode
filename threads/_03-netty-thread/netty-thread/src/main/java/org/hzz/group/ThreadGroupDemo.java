package org.hzz.group;

public class ThreadGroupDemo {
    public static void main(String[] args) {
        // 创建线程组
        ThreadGroup group = new ThreadGroup("MyThreadGroup");

        // 创建线程
        Thread thread1 = new Thread(group, new MyRunnable(), "Thread 1");
        Thread thread2 = new Thread(group, new MyRunnable(), "Thread 2");

        // 设置线程组属性
        group.setMaxPriority(8);
        group.setDaemon(true);

        // 启动线程
        thread1.start();
        thread2.start();

        // 枚举组内的线程
        Thread[] threads = new Thread[group.activeCount()];
        group.enumerate(threads);



        // 挂起组内的所有线程
        group.suspend();

        // 恢复组内的所有线程
        group.resume();

        // 中断组内的所有线程
        group.interrupt();
    }
    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            // 线程执行的逻辑
            while(!Thread.currentThread().isInterrupted()){

            }
            System.out.println("线程执行完毕");
        }
    }
}
