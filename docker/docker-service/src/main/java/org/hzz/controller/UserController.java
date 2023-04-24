package org.hzz.controller;

import org.hzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/insert")
    public int insertUser(String name, String password){
        return userService.insertUser(name, password);
    }

    @RequestMapping("/select")
    public Object selectUser(String name){
        return userService.getUser(name);
    }
}
