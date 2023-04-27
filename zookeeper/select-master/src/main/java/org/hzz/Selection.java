package org.hzz;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.hzz.entity.ServerInfo;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Selection {
    private ServerInfo serverInfo;
    public static final String LEADER_PREFIX = "/hzzApp";
    public static final String LEADER_PATH = "/hzzApp/leader";
    public static final int timeout = 1000;
    private ZooKeeper zooKeeper;
    private int index;
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private volatile boolean started = false;

    public Selection(String ip,int port,int index) throws IOException {
        this.serverInfo = new ServerInfo(ip,port,index);
        this.index = serverInfo.getIndex();
        zooKeeper = new ZooKeeper("localhost:2181", timeout, event -> {
            System.out.println(serverInfo + "监听到事件:" + "---" + event.getState());
            System.out.println(serverInfo + "连接到了zookeeper");
            started = true;
            countDownLatch.countDown();
        });
    }

    public void selection() throws InterruptedException, KeeperException {
        if(!started){
            countDownLatch.await();
        }

        // 创建父节点 /hzzApp
        if(zooKeeper.exists(LEADER_PREFIX,null) == null){
            zooKeeper.create(LEADER_PREFIX,null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // 创建临时节点 /hzzApp/leader
            String path = zooKeeper.create(LEADER_PATH,
                    objectMapper.writeValueAsBytes(serverInfo),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            //竞选成功
            System.out.println(serverInfo + "竞选主节点成功,当前节点id:" + path);
        }catch (Exception e){
            System.out.println(serverInfo + "竞选主节点失败,当前节点已存在");
            //竞选失败,增加对主节点变动的监听
            zooKeeper.getData(LEADER_PATH, event -> {
                System.out.println(index + "监听到事件:" + event.getType());
                System.out.println("检测到节点变动," + serverInfo + "进行竞选");
                try {
                    //重新竞选
                    selection();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            },null);
        }
    }

    // 节点主动下线
    public void close(){
        try {
            zooKeeper.close();
            System.out.println(serverInfo.toString() + "节点下线");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
