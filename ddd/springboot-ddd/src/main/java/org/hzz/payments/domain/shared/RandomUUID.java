package org.hzz.payments.domain.shared;

import java.util.UUID;

public abstract class RandomUUID implements ValueObject<RandomUUID>{
    public final String id;

    public RandomUUID() {
        this.id = String.format(getPrefix(), UUID.randomUUID().toString());
    }

    public RandomUUID(String id) {
        this.id = id;
    }


    @Override
    public boolean sameValueAs(RandomUUID other) {
        return other != null && this.id.equals(other.id);
    }

    protected abstract String getPrefix();
}
