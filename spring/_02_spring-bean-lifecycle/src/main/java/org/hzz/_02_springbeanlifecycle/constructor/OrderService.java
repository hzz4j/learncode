package org.hzz._02_springbeanlifecycle.constructor;

import org.hzz._02_springbeanlifecycle.life.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class OrderService {

    private UserService userService;
    private Long id;


//    @Autowired(required = false)
    public OrderService(UserService userService,UserService userService1){
        System.out.println("有参构造方法2");
        this.userService = userService;
    }

    public OrderService(){
        System.out.println("无参构造方法");
    }

//    @Autowired(required = false)
    public OrderService(UserService userService){
        System.out.println("有参构造方法1");
        this.userService = userService;
    }



    public UserService getUserService(){
        return userService;
    }
}
