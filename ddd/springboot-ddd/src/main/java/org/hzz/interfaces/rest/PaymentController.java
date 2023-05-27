package org.hzz.interfaces.rest;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.callback.Callback;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @GetMapping(path = "hello",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Callable<String> getPayment() {
        return ()->{
            System.out.println("hello");
            return "hello";
        };
    }
}
