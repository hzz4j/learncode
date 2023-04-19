package org.hzz.controller;

import org.hzz.service.GrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GrpcClientController {
    @Autowired
    private GrpcClientService grpcClientService;

    @GetMapping("/printMessage")
    public String printMessage(@RequestParam(defaultValue = "q10viking") String name) {
        return grpcClientService.sendMessage(name);
    }
}
