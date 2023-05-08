package org.hzz.threadquestion;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyBarrier {
    public MyBarrier(){
        TestFactory.getServer();
        log.info("MyBarrier 初始化成功");
    }
}
