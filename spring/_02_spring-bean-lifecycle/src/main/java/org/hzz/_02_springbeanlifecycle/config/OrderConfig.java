package org.hzz._02_springbeanlifecycle.config;

import org.hzz._02_springbeanlifecycle.constructor.OrderService;
import org.hzz._02_springbeanlifecycle.life.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(enforceUniqueMethods = false)
public class OrderConfig {

    @Bean
    public OrderService orderService(){
        System.out.println("run - 1");
        return new OrderService();
    }

    @Bean
    public OrderService orderService(UserService userService){
        System.out.println("run - 2");
        return new OrderService(userService);
    }
}
