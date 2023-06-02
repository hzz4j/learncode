package org.hzz.payments.domain.entity;


import org.hzz.payments.domain.event.PaymentEvent;
import org.hzz.payments.domain.vo.PaymentEventId;

import java.util.concurrent.CompletableFuture;

public interface PaymentEventRepository {
    CompletableFuture<PaymentEventId> store(PaymentEvent eventx);
}
