package org.hzz.payments.domain.event;

import lombok.Value;
import org.hzz.payments.domain.vo.*;

import java.time.LocalDateTime;

@Value(staticConstructor = "eventOf")
public class PaymentRequested implements PaymentEvent {
    private final PaymentEventId eventId = new PaymentEventId();
    private final PaymentEventType eventType = PaymentEventType.PAYMENT_REQUESTED;
    private final PaymentId paymentId;
    private final CustomerId customerId;
    private final PaymentIntent paymentIntent;
    private final PaymentMethod paymentMethod;
    private final Transaction transaction;
    private final LocalDateTime timestamp;
}
