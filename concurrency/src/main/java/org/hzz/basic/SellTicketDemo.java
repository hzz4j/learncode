package org.hzz.basic;

/**
 * 多窗口卖票
 */
public class SellTicketDemo implements Runnable {

    /**
     * 总车票
     */
    private int tickets;

    public SellTicketDemo(int tickets) {
        this.tickets = tickets;
    }

    public static void main(String[] args) {
        SellTicketDemo sellTicketDemo = new SellTicketDemo(100);

        Thread thread1 = new Thread(sellTicketDemo, "thread-1");
        Thread thread2 = new Thread(sellTicketDemo, "thread-2");
        Thread thread3 = new Thread(sellTicketDemo, "thread-3");
        Thread thread4 = new Thread(sellTicketDemo, "thread-4");

        thread1.setPriority(Thread.MIN_PRIORITY);
        thread2.setPriority(Thread.MAX_PRIORITY);
        thread3.setPriority(Thread.MIN_PRIORITY);
        thread4.setPriority(Thread.MAX_PRIORITY);
        startThread(thread1, thread2, thread3, thread4);
    }

    public static void startThread(Thread... threads) {
        for (Thread thread :
                threads) {
            thread.start();
        }
    }

    @Override
    public void run() {
        while (tickets > 0) {
            synchronized (this) {
                try {
                    if (tickets > 0) {
                        Thread.sleep(20);
                        System.out.println(Thread.currentThread().getName() +
                                ": 正在执行操作，余票：" + (--tickets));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Thread.yield();
        }
    }
}
/**
 * output
 * thread-1: 正在执行操作，余票：91
 * thread-1: 正在执行操作，余票：90
 * thread-3: 正在执行操作，余票：89
 * thread-3: 正在执行操作，余票：88
 */