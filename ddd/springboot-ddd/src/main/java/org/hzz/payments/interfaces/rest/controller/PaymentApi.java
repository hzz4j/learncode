package org.hzz.payments.interfaces.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hzz.payments.interfaces.rest.model.PerformPaymentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/payment")
@Tag(name = "支付(Payment)")
public interface PaymentApi {


    @Operation(summary = "支付", description = "支付")
    @ApiResponse(responseCode = "200", description = "支付成功")
    @PostMapping("/process")
    default Callable<CompletableFuture<ResponseEntity<?>>> process(@Valid @RequestBody
                   @Parameter(description = "支付请求", required = true)
                   PerformPaymentRequest performPaymentRequest){
        throw new UnsupportedOperationException("没有实现");
    }
}
