package org.hzz.barrier;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ZkBarrier implements IZKBarrier{

    // 屏障线程的数量
    private final int numbers;
    private final String path;
    private final CuratorFramework curatorFramework;
    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    public ZkBarrier(int numbers){
        this.numbers = numbers;
        this.path = "/barrier" + "-" + UUID.randomUUID();

        this.curatorFramework = CuratorFrameworkdInstance.getCuratorFramework();
        this.init();
    }

    private synchronized void init(){
        try {
            // 创建父节点
            String s = curatorFramework.create()
                    .withMode(CreateMode.CONTAINER)
                    .forPath(path, String.valueOf(numbers).getBytes());

            assert s.equals(path): "创建父节点失败";
        } catch (Exception e) {
            log.info("创建父节点失败");
        }
    }

    @Override
    public void doWait() {
        try {
            // 创建子节点
            String childrenPatn = curatorFramework.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path + "/children-");
            log.info("创建子节点 {}",childrenPatn);
            Watcher watcher = getWatcher();
            List<String> childrens = curatorFramework.getChildren().usingWatcher(watcher).forPath(path);
            if(childrens.size() < numbers) {
                log.info("等待其他线程");
                countDownLatch.await();
                log.info("其他线程已经到达");
            } else {
                log.info("其他线程已经到达");
                breakBarrier();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void breakBarrier(){
        try {
            curatorFramework.delete().deletingChildrenIfNeeded().forPath(path);
            log.info("清除完节点成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Watcher getWatcher(){
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if(event.getType() == Watcher.Event.EventType.NodeChildrenChanged){
                    log.info("监听到容器子节点变化");
                    List<String> childrens = null;
                    try {
                        childrens = curatorFramework.getChildren().forPath(path);
                        if(childrens.size() == numbers){
                            countDownLatch.countDown();
                        }else{
                            // 继续监听
                            curatorFramework.getChildren().usingWatcher(this).forPath(path);
                        }
                    }catch (KeeperException.NoNodeException e){
                        log.info("节点{}已经被删除",path);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        return watcher;
    }
}
