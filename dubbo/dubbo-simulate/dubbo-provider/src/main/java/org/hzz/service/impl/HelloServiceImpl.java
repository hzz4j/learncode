package org.hzz.service.impl;

import org.hzz.service.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String userName) {
        return "Hello: " + userName;
    }
}
