package org.hzz.zk;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class MyFactory {
    private static Object lock = new Object();
    static{
//        init();
        new Thread(()->{
            init();
        }).start();
    }

    private static void init(){
        new Thread(()->{
            try {
                // make this thread run after main thread
                TimeUnit.SECONDS.sleep(3);
                log.info("task run...");
                synchronized (lock){
                    lock.notifyAll();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"demo-thread").start();

        synchronized (lock){
            try {
                log.info("waiting...");
                lock.wait();
                log.info("wake up");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static Object getSomething(){
        return null;
    }
}

/**
 *
 2023-04-29 21:24:17.794 [INFO ] org.hzz.zk.MyFactory [main] : waiting...
 2023-04-29 21:24:20.798 [INFO ] org.hzz.zk.MyFactory [demo-thread] : task run...
 2023-04-29 21:24:20.799 [INFO ] org.hzz.zk.MyFactory [main] : wake up
 2023-04-29 21:24:17.794 [INFO ] org.hzz.zk.Main [main] : over
 */
