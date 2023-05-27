package org.hzz.payments.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hzz.payments.domain.shared.ValueObject;
import org.hzz.payments.infrastructure.util.validation.ValidEnum;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Money implements ValueObject<Money> {
    @ValidEnum(conformsTo = CurrencyCodes.class)
    public final String currency; // 货币
    @NotNull
    public final Integer amount; // 金额
    @NotNull
    public final Integer scale; // 精度
    @JsonIgnore
    public final BigDecimal amountAsBigDecimal; // 金额

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Money(
            @JsonProperty("currency")
            String currency,
            @JsonProperty("amount")
            Integer amount,
            @JsonProperty("scale")
            Integer scale) {
        this.currency = currency;
        this.amount = amount;
        this.scale = scale;
        this.amountAsBigDecimal = new BigDecimal(amount).movePointLeft(2).setScale(scale);
    }
    @Override
    public boolean sameValueAs(Money other) {
        return other != null &&
                this.currency.equals(other.currency) &&
                this.amount.equals(other.amount) &&
                this.scale.equals(other.scale);
    }
}
