package org.hzz.payments.domain.command;

import lombok.Value;
import org.hzz.payments.domain.vo.CustomerId;
import org.hzz.payments.domain.vo.PaymentIntent;
import org.hzz.payments.domain.vo.PaymentMethod;
import org.hzz.payments.domain.vo.Transaction;

import java.time.LocalDateTime;

@Value(staticConstructor = "commandOf")
public class PerformPayment implements PaymentCommand{
    private final CustomerId customerId;
    private final PaymentIntent paymentIntent;
    private final PaymentMethod paymentMethod;
    private final Transaction transaction;
    private final LocalDateTime timestamp = LocalDateTime.now();
}
