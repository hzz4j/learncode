package org.hzz;

import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        ReferenceConfig<GreetingService> reference = new ReferenceConfig<>();
        reference.setInterface(GreetingService.class);

        DubboBootstrap.getInstance()
                .application("first-dubbo-consumer")
                .registry(new RegistryConfig("zookeeper://172.29.96.105:2181"))
                .reference(reference);

        GreetingService service = reference.get();
        String message = service.sayHi("Q10Viking");
        System.out.println("Receive result ======> " + message);
        System.in.read();
    }
}
