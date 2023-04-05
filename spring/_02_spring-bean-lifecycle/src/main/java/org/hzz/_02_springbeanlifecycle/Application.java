package org.hzz._02_springbeanlifecycle;

import org.hzz._02_springbeanlifecycle.life.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        UserService userService = (UserService)context.getBean("userService");
        Map<String, Object> systemProperties = userService.getSystemProperties();
        System.out.println(systemProperties);

//        OrderService orderService = (OrderService)context.getBean("orderService", userService);
//        OrderService orderService = (OrderService)context.getBean("orderService");

//        System.out.println(orderService.getUserService() == userService);


    }

}
