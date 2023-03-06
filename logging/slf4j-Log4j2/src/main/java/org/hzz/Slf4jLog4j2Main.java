package org.hzz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLog4j2Main {
    private static final Logger LOG = LoggerFactory.getLogger(Slf4jLog4j2Main.class);
    public static void main(String[] args) {
        LOG.info("Hello World");
    }
}
/**
 * 2023-03-07 03:29:08 [main] INFO  org.hzz.Slf4jLog4j2Main - Hello World
 */