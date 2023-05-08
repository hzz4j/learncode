package org.hzz;

import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

public class Application {
    public static void main(String[] args) {
        // 定义具体的服务
        ServiceConfig<GreetingService> service = new ServiceConfig<>();
        service.setInterface(GreetingService.class);
        service.setRef(new GreetingServiceImpl());

        // 启动 Dubbo
        DubboBootstrap.getInstance()
                .application("first-dubbo-provider")
                .registry(new RegistryConfig("zookeeper://172.29.96.105:2181"))
                .protocol(new ProtocolConfig("dubbo", -1))
                .service(service)
                .start()
                .await();
    }
}
