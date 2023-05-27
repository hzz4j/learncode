package org.hzz.payments.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hzz.payments.domain.shared.ValueObject;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TransactionItem implements ValueObject<TransactionItem> {
    @NotNull
    @Size(min = 3)
    public final String name;
    @Valid
    public final Money price;

    @Min(1)
    public final Integer quantity; // 数量

    @JsonCreator
    public TransactionItem(
            @JsonProperty("name")
            String name,
            @JsonProperty("price")
            Money price,
            @JsonProperty("quantity")
            int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    @Override
    public boolean sameValueAs(TransactionItem other) {
        return other != null &&
                this.name.equals(other.name) &&
                this.price.sameValueAs(other.price) &&
                this.quantity.equals(other.quantity);
    }
}
