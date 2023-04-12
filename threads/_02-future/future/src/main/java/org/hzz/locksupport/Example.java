package org.hzz.locksupport;


import java.util.concurrent.locks.LockSupport;

public class Example {
    public static void main(String[] args) throws InterruptedException {
        Object blocker = new Object();
        Thread thread = new Thread(() -> {
            System.out.println("Thread is parking...");
            LockSupport.park(); // Thread will block here
            // LockSupport.park(blocker); // Thread will block here
            System.out.println("Thread is unparked!");
        });

        thread.start();
        Thread.sleep(2000); // Sleep for 2 seconds
        System.out.println("Unparking thread...");
        LockSupport.unpark(thread); // Unpark the thread
    }
}
/**
 * Thread is parking...
 * Unparking thread...
 * Thread is unparked!
 */