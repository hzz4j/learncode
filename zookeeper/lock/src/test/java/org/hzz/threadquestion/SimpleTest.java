package org.hzz.threadquestion;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SimpleTest {
    private static Object lock = new Object();
    private static List<Runnable> task = new ArrayList<>();
    private static ExecutorService service = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        log.info("main start");
        task.add(()->{
            synchronized (lock){
                lock.notifyAll();
            }
        });
        service.execute(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                task.forEach(t -> t.run());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


        synchronized (lock){
            try {
                log.info("waiting...");
                lock.wait();
                log.info("wake up");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("main over.");
    }
}
