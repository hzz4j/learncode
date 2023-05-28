package org.hzz.payments.domain.command.handler;

import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.hzz.payments.domain.command.PerformPayment;
import org.hzz.payments.domain.event.PaymentPerformedEvent;
import org.hzz.payments.domain.shared.CommandFailure;
import org.hzz.payments.domain.shared.CommandHandler;
import org.hzz.payments.domain.vo.PaymentId;
import org.springframework.stereotype.Component;


import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class PerformPaymentHandler implements CommandHandler<PerformPayment, PaymentPerformedEvent, PaymentId> {
    @Override
    public CompletableFuture<Either<CommandFailure, PaymentPerformedEvent>> handle(PerformPayment command, PaymentId entityId) {
        return null;
    }
}
