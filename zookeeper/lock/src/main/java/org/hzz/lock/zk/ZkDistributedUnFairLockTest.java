package org.hzz.lock.zk;

import lombok.extern.slf4j.Slf4j;
import org.hzz.lock.Lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ZkDistributedUnFairLockTest implements Runnable{

    private static int productCount = 10;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    @Override
    public void run() {
        Lock lock = new ZkDistributedUnFairLock("/product001");
        try {
            countDownLatch.await();
            lock.lock();
            // 模拟扣减库存
            productCount--;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        while (!ZookeeperFactory.isStarted()){}
        log.info("zk client is started");

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            log.info("productCount = {}",productCount);
        }));

        for(int i=0;i<10;i++){
            new Thread(new ZkDistributedUnFairLockTest()).start();
        }


        // 等待线程都到达执行点
        TimeUnit.SECONDS.sleep(3);
        countDownLatch.countDown();
        //线程一直在阻塞，无法终止。自己等待自己结束
//        Thread.currentThread().join();
        log.info("main thread exit");
    }
}
