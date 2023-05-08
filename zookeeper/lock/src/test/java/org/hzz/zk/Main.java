package org.hzz.zk;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        MyFactory.getSomething();
        log.info("over");  // not print
    }
}
