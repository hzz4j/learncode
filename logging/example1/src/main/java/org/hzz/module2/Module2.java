package org.hzz.module2;
import org.hzz.module1.Module1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Module2 {
    private static final Logger LOG = LoggerFactory.getLogger(Module2.class);
    public void hello(){
        LOG.info("Model2 say:Hello World");
        new Module1().hello();
    }

    public static void main(String[] args) {
        new Module2().hello();
    }
}
