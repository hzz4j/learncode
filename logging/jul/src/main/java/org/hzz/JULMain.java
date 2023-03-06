package org.hzz;

import java.util.logging.Logger;

public class JULMain {
    private final static Logger LOG = Logger.getLogger(JULMain.class.getName());
    public static void main(String[] args) {
        LOG.info("hello world");
        LOG.info("hello Q10Viking");
    }
}
/**
 * 信息: hello world [星期二 三月 07 01:10:07 GMT+08:00 2023]
 */