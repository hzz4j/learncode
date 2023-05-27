package org.hzz.payments.interfaces.rest.model;

import lombok.Data;

@Data
public class PerformPaymentRequest {
    private String custormerId;
    private String paymentIntent;   // 付款意向
    private String paymentMethod;   // 付款方式

}
