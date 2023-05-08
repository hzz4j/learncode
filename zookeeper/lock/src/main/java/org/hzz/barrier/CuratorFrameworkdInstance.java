package org.hzz.barrier;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.sql.Time;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CuratorFrameworkdInstance {
    private  static CuratorFramework curatorFramework;
    private  static final Object object = new Object();
    private  static volatile boolean started = false;

    static{
        new Thread(()->{
            try {
                init();
            }catch (InterruptedException e){}
        },"hzz-thread").start();
    }
    private static void init() throws InterruptedException {
        log.info("init");
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .sessionTimeoutMs(60*1000)
                .connectionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();

        curatorFramework.getConnectionStateListenable().addListener((client, newState) -> {
            log.info("连接状态改变");
            if(newState == ConnectionState.CONNECTED) {
                log.info("连接成功");
                started = true;
                synchronized (object){
                    object.notifyAll();
                }
            } else if(newState == ConnectionState.LOST) {
                log.info("连接断开");
                started = false;
            } else if(newState == ConnectionState.RECONNECTED) {
                log.info("重连成功");
                started = true;
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            curatorFramework.close();
            log.info("关闭连接");
        }));

        curatorFramework.start();
        synchronized (object){
            try {
                log.info("连接中...");
                curatorFramework.start();
                object.wait();
                log.info("被唤醒，连接成功");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static CuratorFramework getCuratorFramework(){
        while (!started){
            log.info("等待连接中。。。");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return curatorFramework;
    }
}
