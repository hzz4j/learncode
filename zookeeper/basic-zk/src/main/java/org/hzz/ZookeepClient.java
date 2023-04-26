package org.hzz;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ZookeepClient {

    private final static  String CONNECT_STR="localhost:2181";
    private final static Integer  SESSION_TIMEOUT=30*1000;
    private static ZooKeeper zooKeeper=null;
    private static CountDownLatch countDownLatch=new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper(CONNECT_STR, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getType() == Event.EventType.None
                        && event.getState() == Event.KeeperState.SyncConnected) {
                    log.info("连接已建立");
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();

//===========================创建节点================================================
        MyConfig myConfig = new MyConfig();
        myConfig.setKey("hzz");
        myConfig.setValue("Q10Viking");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(myConfig);
        String s = zooKeeper.create("/myconfig", bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        //===========================设置监听==========================================
        Watcher watcher = new Watcher() {
            @SneakyThrows
            @Override
            public void process(WatchedEvent event) {
                if (event.getType() == Event.EventType.NodeDataChanged
                        && event.getPath() != null && event.getPath().equals("/myconfig")) {
                    log.info(" PATH:{}  发生了数据变化", event.getPath());
                    // 设置循环监听
                    byte[] data = zooKeeper.getData("/myconfig", this, null);
                    MyConfig newConfig = objectMapper.readValue(new String(data), MyConfig.class);
                    log.info("数据发生变化: {}", newConfig);
                }
            }
        };
        // 获取数据
        byte[] data = zooKeeper.getData("/myconfig", watcher, null);
        MyConfig originalMyConfig = objectMapper.readValue(new String(data), MyConfig.class);
        log.info("原始数据: {}", originalMyConfig);

        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
/**
 * 2023-04-26 22:44:44.142 [INFO ] org.hzz.ZookeepClient [main-EventThread] : 连接已建立
 * 2023-04-26 22:44:44.318 [INFO ] org.hzz.ZookeepClient [main] : 原始数据: MyConfig(key=hzz, value=Q10Viking)
 */