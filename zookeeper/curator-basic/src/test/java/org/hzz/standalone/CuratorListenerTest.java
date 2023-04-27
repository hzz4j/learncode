package org.hzz.standalone;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.junit.Test;

@Slf4j
public class CuratorListenerTest extends CuratorBaseOperations{

    @Test
    public void testCuratorListener() throws Exception {
        CuratorFramework curatorFramework = getCuratorFramework();
        curatorFramework.getCuratorListenable().addListener((client, event) -> {
            log.info("event type:{}", event.getType());
            log.info("event path:{}", event.getPath());
            log.info("event state:{}", event.getName());
        });

        String path = "/test";
        createIfNeed(path);
        log.info("start to change data for path :{}" ,path);
        curatorFramework.setData().inBackground().forPath(path,"xxx1".getBytes());
        log.info("start to change data for path :{} again" ,path);
        curatorFramework.setData().inBackground().forPath(path,"xxx2".getBytes());
        curatorFramework.create().inBackground().forPath("/test456","test456".getBytes());
    }
    /**
     *  [main-EventThread] : event type:SET_DATA
     *  [main-EventThread] : event path:/test
     *  [main-EventThread] : event state:null
     *  [main-EventThread] : event type:SET_DATA
     *  [main-EventThread] : event path:/test
     *  [main-EventThread] : event state:null
     *  [main-EventThread] : event type:CREATE
     *  [main-EventThread] : event path:/test456
     *  [main-EventThread] : event state:/test456
     */
}
