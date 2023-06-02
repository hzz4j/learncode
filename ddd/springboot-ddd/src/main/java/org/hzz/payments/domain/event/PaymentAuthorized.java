package org.hzz.payments.domain.event;

import lombok.Value;
import org.hzz.payments.domain.vo.*;

import java.time.LocalDateTime;

@Value(staticConstructor = "eventOf")
public class PaymentAuthorized implements PaymentEvent{
    private final PaymentEventId eventId = new PaymentEventId();
    private final PaymentId paymentId;
    private final CustomerId customerId;
    private final PaymentMethod paymentMethod;
    private final Transaction transaction;
    private final LocalDateTime timestamp;

    @Override
    public PaymentEventType getEventType() {
        return PaymentEventType.PAYMENT_AUTHORIZED;
    }
}
