package org.hzz.payments.interfaces.rest.controller;


import org.hzz.payments.interfaces.rest.model.PerformPaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;


@RestController
public class PaymentController implements PaymentApi{
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Override
    public Callable<CompletableFuture<ResponseEntity<?>>> process(PerformPaymentRequest performPaymentRequest) {
        log.info("request:{}",performPaymentRequest);

        return ()->{
            log.info("callable");
            return CompletableFuture.completedFuture(ResponseEntity.ok().build());
        };
    }
}
