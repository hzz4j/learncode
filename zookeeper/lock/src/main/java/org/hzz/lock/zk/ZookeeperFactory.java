package org.hzz.lock.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

@Slf4j
public class ZookeeperFactory {
    private static ZooKeeper zooKeeper;
    private static volatile boolean started = false;
    static {
        getZookeeper("localhost", 2181);
    }
    private ZookeeperFactory(){}
    public static ZooKeeper getZookeeper(String host, int port) {
        if(zooKeeper == null){
            synchronized (ZookeeperFactory.class){
                if(zooKeeper == null){
                    try {
                        zooKeeper = new ZooKeeper(host + ":" + port, 1000, event -> {
                            if(event.getState() == Watcher.Event.KeeperState.SyncConnected){
                                log.info("zookeeper connected {} : {}", host, port);
                                started = true;
                            }
                        });
                        addShutdownHook();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return zooKeeper;
    }

    private static void addShutdownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            try {
                zooKeeper.close();
                log.info("zookeeper 关闭");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }

    public static boolean isStarted(){
        return started;
    }
}