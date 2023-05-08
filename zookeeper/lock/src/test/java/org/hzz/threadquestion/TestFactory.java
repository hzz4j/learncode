package org.hzz.threadquestion;

import lombok.extern.slf4j.Slf4j;
import org.hzz.threadquestion.curator.MyCurator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TestFactory {

    private static MyCurator curator;
    private static volatile boolean started = false;
    static {

        new Thread(()->{
            try {
                init();
            } catch (InterruptedException e) {}
        },"hzz").start();
//        try {
//            init();
//        } catch (InterruptedException e) {}
    }

    private static void init() throws InterruptedException {
        curator = new MyCurator();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        curator.getConnectionStateListenable().add(((client, newState) -> {
            log.info("检测到状态改变");
            started = true;
            countDownLatch.countDown();
        }));

        curator.start();
        log.info("等待被唤醒");
        countDownLatch.await();
        log.info("被唤醒");
    }

    public static void getServer(){
        log.info("hello");
        while(!started){
//            log.info("waiting...");
        }
    }
}
