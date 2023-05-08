package org.hzz;

import org.apache.dubbo.config.annotation.DubboService;
import org.hzz.demo.DemoService;

@DubboService
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name + "(Hello World)";
    }
}
