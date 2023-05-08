package org.hzz.controller;

import org.hzz.entity.Result;
import org.hzz.feign.OrderFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private OrderFeignService orderFeignService;

    @GetMapping("/findOrderByUserId/{id}")
    public Result findOrderByUserId(@PathVariable("id") Integer id){
        return orderFeignService.findOrderByUserId(id);
    }
}
