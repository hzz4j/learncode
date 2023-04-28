package org.hzz.lock.zk.id;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.hzz.lock.zk.ZookeeperFactory;

import java.util.concurrent.Executor;

@Slf4j
public class IDMaker {

    private final ZooKeeper zooKeeper;
    private final String path = "/idmaker/id-";

    public IDMaker(){
        while(!ZookeeperFactory.isStarted()){}
        zooKeeper = ZookeeperFactory.getZookeeper();
        try {
            zooKeeper.create("/idmaker", null,
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.CONTAINER, null);
        } catch (KeeperException | InterruptedException e) {
            log.info("idmaker container exists");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Executor executor = command -> {
            Thread thread = new Thread(command);
            thread.start();
        };

        IDMaker idMaker = new IDMaker();

        for(int i = 0; i < 5; i++){
            executor.execute(()->{
                String id = idMaker.makeId();
                log.info("id: {}", id);
            });
        }

        Thread.currentThread().join();
    }

    public String makeId(){
        String destPath = createEphemeralSequential(path);
        if(destPath != null){
            int index = destPath.lastIndexOf(path);
            if(index >= 0){
                index += path.length();
                return index <= destPath.length() ? destPath.substring(index) : "";
            }
        }
        return "";
    }

    private String createEphemeralSequential(String path){
        try {
            String destPath = zooKeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, null);
            log.info("createEphemeralSequential: {}", destPath);
            return destPath;
        } catch (KeeperException  | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
/**
 * IDMaker [Thread-1] : createEphemeralSequential: /idmaker/id-0000000000
 * IDMaker [Thread-1] : id: 0000000000
 * IDMaker [Thread-2] : createEphemeralSequential: /idmaker/id-0000000001
 * IDMaker [Thread-2] : id: 0000000001
 * IDMaker [Thread-3] : createEphemeralSequential: /idmaker/id-0000000002
 * IDMaker [Thread-3] : id: 0000000002
 * IDMaker [Thread-4] : createEphemeralSequential: /idmaker/id-0000000003
 * IDMaker [Thread-4] : id: 0000000003
 * IDMaker [Thread-5] : createEphemeralSequential: /idmaker/id-0000000004
 * IDMaker [Thread-5] : id: 0000000004
 */