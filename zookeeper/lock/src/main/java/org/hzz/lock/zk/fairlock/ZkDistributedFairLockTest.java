package org.hzz.lock.zk.fairlock;

import lombok.extern.slf4j.Slf4j;
import org.hzz.lock.Lock;
import org.hzz.lock.zk.ZookeeperFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ZkDistributedFairLockTest implements Runnable{

    private static int productCount = 10;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    @Override
    public void run() {
        Lock lock = new ZkDistributedFairLock("/product002");
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

        for(int i=1;i<=5;i++){
            new Thread(new ZkDistributedFairLockTest(),"[hzz-thread-"+i+"]").start();
        }


        // 等待线程都到达执行点
        TimeUnit.SECONDS.sleep(3);
        countDownLatch.countDown();
        //线程一直在阻塞，无法终止。自己等待自己结束
//        Thread.currentThread().join();
        log.info("main thread exit");
    }
}
