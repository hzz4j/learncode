package org.hzz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jJULMain {
    private static final Logger LOG = LoggerFactory.getLogger(Slf4jJULMain.class);
    public static void main(String[] args) {
        LOG.info("Hello World");
    }
}
/**
 * 三月 07, 2023 2:46:37 上午 org.hzz.Slf4jJULMain main
 * 信息: Hello World
 */