package org.hzz.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.hzz.user.service.UserService;
@RestController
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping("/test")
    public String test(){
        return userService.test();
    }
}
