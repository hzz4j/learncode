package org.hzz;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTest {
    @Test
    public void test(){
        Logger logger = LoggerFactory.getLogger(LogbackTest.class);
        logger.info("Hello World");
    }
}
/**
 * 14:41:04.765 [main] INFO org.hzz.LogbackTest - Hello World
 */