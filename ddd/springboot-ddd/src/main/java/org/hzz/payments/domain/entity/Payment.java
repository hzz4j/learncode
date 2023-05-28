package org.hzz.payments.domain.entity;

import org.hzz.payments.domain.command.PerformPayment;
import org.hzz.payments.domain.shared.AggregateRoot;
import org.hzz.payments.domain.vo.PaymentId;
import org.springframework.context.ApplicationContext;

public class Payment extends AggregateRoot<Payment, PaymentId> {
    protected Payment(PaymentId paymentId, ApplicationContext applicationContext) {
        super(paymentId, applicationContext);
    }

    @Override
    protected AggregateRoot<Payment, PaymentId>.AggregateRootBehavior initialBehavior() {
        return new AggregateRootBehaviorBuilder()
                .setCommandHandler(PerformPayment.class,null)
                .build();

    }

    @Override
    public boolean sameIdentityAs(Payment other) {
        return other != null && entityId.sameValueAs(other.entityId);
    }

    @Override
    public PaymentId identity() {
        return entityId;
    }
}
