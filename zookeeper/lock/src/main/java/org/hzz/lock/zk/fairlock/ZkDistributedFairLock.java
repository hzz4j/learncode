package org.hzz.lock.zk.fairlock;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.hzz.lock.AbstractLock;
import org.hzz.lock.zk.ZookeeperFactory;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class ZkDistributedFairLock extends AbstractLock {
    private ZooKeeper zookeeper = ZookeeperFactory.getZookeeper("localhost", 2181);

    // 如果Container节点下面没有子节点，则Container节点在未来会被Zookeeper**自动清除**,定时任务默认60s 检查一次
    private String lockResource;
    private String currentPath;
    public ZkDistributedFairLock(String lockResource) {
        this.lockResource = lockResource;
        try {
            if(zookeeper.exists(this.lockResource,false) == null){
                // 不存在创建一个容器节点
                zookeeper.create(this.lockResource,"".getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.CONTAINER);
            }
        } catch (KeeperException | InterruptedException e) {

        }
    }

    @Override
    protected boolean tryLock() {
        try {
            // 创建临时有序节点
            //   /product001/0000000000
            if(currentPath == null){
                currentPath = zookeeper.create(lockResource + "/", "".getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            }

            // 获取容器节点下面的所有子节点
            // ["0000000001","0000000002"]
            List<String> childrens = zookeeper.getChildren(lockResource, false);

            // 排序
            childrens.sort(String::compareTo);
            if(isFirstOne(childrens)){
                return true;
            }
            // 获取当前节点的前一个节点
            String preNode = childrens.get(childrens.indexOf(currentPath.substring(lockResource.length()+1))-1);
            // 监听前一个节点的删除事件
            zookeeper.getData(lockResource+"/"+preNode, event -> {
                if(event.getType() == Watcher.Event.EventType.NodeDeleted){
                    synchronized (this){
                        notifyAll();
                    }
                }
            }, null);
            return false;
        } catch (KeeperException | InterruptedException e) {
            // 前一个节点不存在，继续获取锁
            return tryLock();
        }
    }

    private boolean isFirstOne(List<String> childrens) {
        // 获取当前节点的位置
        int index = childrens.indexOf(currentPath.substring(lockResource.length()+1));
//        log.info(currentPath.substring(lockResource.length()+1));
//        log.info(Arrays.toString(childrens.toArray()));
        switch (index){
            case -1:
                System.out.println(Thread.currentThread().getName()+": 节点不存在");
                return false;
            case 0:
                System.out.println(Thread.currentThread().getName()+": 当前节点为第1个节点");
                return true;
            default:
                System.out.println(Thread.currentThread().getName()+": 当前节点排在第"+(index+1)+"位");
                return false;
        }
    }

    @Override
    protected synchronized void waitLock() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unlock() {
        try {
            System.out.println(Thread.currentThread().getName()+": 释放锁");
            zookeeper.delete(currentPath,-1);
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }
}
