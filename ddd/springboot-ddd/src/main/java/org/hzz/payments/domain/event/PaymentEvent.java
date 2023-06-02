package org.hzz.payments.domain.event;

import org.hzz.payments.domain.shared.Event;
import org.hzz.payments.domain.vo.PaymentEventId;
import org.hzz.payments.domain.vo.PaymentEventType;
import org.hzz.payments.domain.vo.PaymentId;

import java.time.LocalDateTime;

public interface PaymentEvent extends Event {
    PaymentEventId getEventId();

    PaymentEventType getEventType();

    PaymentId getPaymentId();

    LocalDateTime getTimestamp();
}
