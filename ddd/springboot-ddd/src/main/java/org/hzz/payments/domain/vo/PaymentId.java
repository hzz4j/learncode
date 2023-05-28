package org.hzz.payments.domain.vo;

import org.hzz.payments.domain.shared.RandomUUID;

public class PaymentId extends RandomUUID {
    public PaymentId(String id) {
        super(id);
    }
    @Override
    protected String getPrefix() {
        return "PAY-%s";
    }
}
