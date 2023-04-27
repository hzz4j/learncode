package org.hzz.lock.zk.unfairlock;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.hzz.lock.AbstractLock;
import org.hzz.lock.zk.ZookeeperFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 非公平锁的分布式锁
 */
@Slf4j
public class ZkDistributedUnFairLock extends AbstractLock {
    private ZooKeeper zooKeeper;
    private String lockResource;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public ZkDistributedUnFairLock(String lockResource) {
        this.zooKeeper = ZookeeperFactory.getZookeeper("localhost", 2181);
        this.lockResource = lockResource;
    }

    @Override
    protected boolean tryLock() {
        try {
            String path = zooKeeper.create(lockResource, null,
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.println(Thread.currentThread().getName() + " get lock");
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    protected void waitLock() {
        try {
            zooKeeper.getData(lockResource, (event) -> {
                if(event.getType() == Watcher.Event.EventType.NodeDeleted){
//                    log.info("节点被删除，被唤醒重新获取锁");
                    countDownLatch.countDown();
                }
            }, null);
            log.info("等待锁");
            countDownLatch.await();
            log.info("节点被删除，被唤醒重新获取锁");
        } catch (KeeperException | InterruptedException e) {
            // 重新竞争锁 因为上面getData的时候,如果节点不存在会抛出异常
        }
    }

    @Override
    public void unlock() {
        try {
            zooKeeper.delete(lockResource, -1);
            log.info("释放锁");
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }
}
