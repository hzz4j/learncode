package org.hzz.payments.interfaces.rest.controller;


import org.hzz.payments.interfaces.rest.model.PerformPaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/payment")
public class PaymentController {
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping("/process")
    public Object process(){
        new PerformPaymentRequest();
        return null;
    }
}
