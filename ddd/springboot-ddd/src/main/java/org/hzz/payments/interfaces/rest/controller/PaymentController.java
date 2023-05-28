package org.hzz.payments.interfaces.rest.controller;


import org.hzz.payments.domain.command.PerformPayment;
import org.hzz.payments.domain.vo.CustomerId;
import org.hzz.payments.domain.vo.PaymentIntent;
import org.hzz.payments.domain.vo.PaymentMethod;
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
    public Callable<CompletableFuture<ResponseEntity<?>>> process(PerformPaymentRequest request) {
        log.info("request:{}",request);

        return ()->{
            log.info("callable");
            PerformPayment performPayment = PerformPayment.commandOf(
                    new CustomerId(request.getCustormerId()),
                    PaymentIntent.valueOf(request.getPaymentIntent()),
                    PaymentMethod.valueOf(request.getPaymentMethod()),
                    request.getTransaction()
            );

            return CompletableFuture.completedFuture(ResponseEntity.ok().build());
        };
    }
}
