package org.hzz.payments.domain.vo;

import org.hzz.payments.domain.shared.RandomUUID;

public class CustomerId extends RandomUUID {
    public CustomerId(String id) {
        super(id);
    }
    @Override
    protected String getPrefix() {
        return "CST-%s";
    }
}
