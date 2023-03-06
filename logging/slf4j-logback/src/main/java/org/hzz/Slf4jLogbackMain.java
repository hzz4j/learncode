package org.hzz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLogbackMain {
    private static final Logger LOG = LoggerFactory.getLogger(Slf4jLogbackMain.class);
    public static void main(String[] args) {
        LOG.info("Hello World");
    }
}
/**
 * 2023-03-07 03:01:36.181 [INFO ] org.hzz.Slf4jLogbackMain [main] : Hello World
 */