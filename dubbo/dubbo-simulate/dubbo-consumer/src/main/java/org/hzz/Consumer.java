package org.hzz;

import org.hzz.dubbo.ProxyFactory;
import org.hzz.service.HelloService;

public class Consumer {
    public static void main(String[] args) {

        // 创建本地存根
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        // 进行调用
        String xxx = helloService.sayHello("hzz");
        System.out.println(xxx);
    }
}
