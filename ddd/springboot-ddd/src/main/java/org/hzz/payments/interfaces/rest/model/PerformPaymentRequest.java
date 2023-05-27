package org.hzz.payments.interfaces.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hzz.payments.domain.vo.Transaction;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class PerformPaymentRequest {
    @Schema(description = "客户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String custormerId;
    @Schema(description = "货币代码(付款意向)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String paymentIntent;
    @Schema(description = "付款方式", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String paymentMethod;

    @Schema(description = "交易", requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    private Transaction transaction;
}
