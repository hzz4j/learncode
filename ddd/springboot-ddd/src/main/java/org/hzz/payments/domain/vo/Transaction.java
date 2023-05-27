package org.hzz.payments.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hzz.payments.domain.shared.ValueObject;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class Transaction implements ValueObject<Transaction> {
    @Valid
    public final Money amount;

    @NotNull
    @Size(min = 1)
    public final List< @Valid TransactionItem> items;

    @JsonCreator
    public Transaction(
            @JsonProperty("amount")
            Money amount,
            @JsonProperty("items")
            List<TransactionItem> items) {
        this.amount = amount;
        this.items = items;
    }

    @Override
    public boolean sameValueAs(Transaction other) {
        return other != null &&
                this.amount.sameValueAs(other.amount) &&
                this.items.equals(other.items);
    }
}
