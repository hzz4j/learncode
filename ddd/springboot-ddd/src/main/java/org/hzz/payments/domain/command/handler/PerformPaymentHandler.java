package org.hzz.payments.domain.command.handler;

import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.hzz.payments.domain.command.PerformPayment;
import org.hzz.payments.domain.command.validation.PerformPaymentValidator;
import org.hzz.payments.domain.entity.PaymentEventRepository;
import org.hzz.payments.domain.event.PaymentRequested;
import org.hzz.payments.domain.shared.CommandFailure;
import org.hzz.payments.domain.shared.CommandHandler;
import org.hzz.payments.domain.vo.PaymentEventId;
import org.hzz.payments.domain.vo.PaymentId;
import org.springframework.stereotype.Component;


import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class PerformPaymentHandler implements CommandHandler<PerformPayment, PaymentRequested, PaymentId> {

    private final PaymentEventRepository paymentEventRepository;
    private final PerformPaymentValidator performPaymentValidator;
    public PerformPaymentHandler(PaymentEventRepository paymentEventRepository,
                                 PerformPaymentValidator performPaymentValidator){
        this.paymentEventRepository = paymentEventRepository;
        this.performPaymentValidator = performPaymentValidator;
    }

    @Override
    public CompletableFuture<Either<CommandFailure, PaymentRequested>> handle(PerformPayment command, PaymentId entityId) {
        log.info("Handle Command {}", command);
        return performPaymentValidator.acceptOrReject(command)
                .fold(
                        reject -> CompletableFuture.completedFuture(Either.left(reject)),
                        accept -> {
                            PaymentRequested paymentRequest = PaymentRequested.eventOf(
                                    entityId,
                                    accept.getCustomerId(),
                                    accept.getPaymentIntent(),
                                    accept.getPaymentMethod(),
                                    accept.getTransaction(),
                                    accept.getTimestamp()
                            );

                            return paymentEventRepository.store(paymentRequest)
                                    .thenApply(paymentEventId -> Either.right(paymentRequest));
                        }
                );
    }
}
