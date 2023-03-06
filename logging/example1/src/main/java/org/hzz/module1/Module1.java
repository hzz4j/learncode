package org.hzz.module1;

import org.apache.log4j.Logger;

public class Module1 {
    private static Logger LOG = Logger.getLogger(Module1.class);
    public void hello(){
        LOG.info("Model1 say:Hello World");
    }
}
