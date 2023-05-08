package org.hzz;

import org.hzz.dubbo.Protocol;
import org.hzz.dubbo.ProtocolFactory;
import org.hzz.dubbo.URL;
import org.hzz.dubbo.register.LocalRegister;
import org.hzz.dubbo.register.RemoteMapRegister;
import org.hzz.dubbo.register.ZookeeperRegister;
import org.hzz.service.HelloService;
import org.hzz.service.impl.HelloServiceImpl;

public class Provider {
    private static boolean isRun = true;

    public static void main(String[] args) {
        // 1. 注册服务
        // 2. 本地注册
        // 3. 启动tomcat

        // 注册服务
        URL url = new URL("localhost", 8888); //NetUtil
//        RemoteMapRegister.regist(HelloService.class.getName(), url);
        ZookeeperRegister.regist(HelloService.class.getName(), url);

        //  服务：实现类
        LocalRegister.regist(HelloService.class.getName(), HelloServiceImpl.class);


        Protocol protocol = ProtocolFactory.getProtocol();
        protocol.start(url);


    }
}
