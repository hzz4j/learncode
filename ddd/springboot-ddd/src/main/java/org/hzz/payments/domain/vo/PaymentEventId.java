package org.hzz.payments.domain.vo;

import org.hzz.payments.domain.shared.RandomUUID;

public class PaymentEventId extends RandomUUID {
    @Override
    protected String getPrefix() {
        return "PEV_%s";
    }
}
