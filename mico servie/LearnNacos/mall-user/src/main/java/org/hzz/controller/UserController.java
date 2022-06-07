package org.hzz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/demo")
    public String demo(){
        String url = "http://mall-order/order/demo";
        String result = restTemplate.getForObject(url, String.class);
        return "userDemo: hello world \n "+result;
    }
}
