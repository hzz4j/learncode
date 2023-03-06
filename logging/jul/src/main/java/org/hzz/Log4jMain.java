package org.hzz;
import org.apache.log4j.Logger;

public class Log4jMain {
    private static Logger LOG = Logger.getLogger(Log4jMain.class);
    public static void main(String[] args) {
        LOG.info("Hello World");
    }
}
/**
 * [INFO ] 2023-03-07 01:39:37,941 method :org.hzz.Log4jMain.main(Log4jMain.java:7)
 * Hello World
 */