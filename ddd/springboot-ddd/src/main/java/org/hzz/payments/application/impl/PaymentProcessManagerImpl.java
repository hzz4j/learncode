package org.hzz.payments.application.impl;

import io.vavr.Tuple2;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.hzz.payments.application.PaymentProcessManager;
import org.hzz.payments.domain.command.PaymentCommand;
import org.hzz.payments.domain.entity.Payment;
import org.hzz.payments.domain.event.PaymentAuthorized;
import org.hzz.payments.domain.shared.CommandFailure;
import org.hzz.payments.domain.shared.Event;
import org.hzz.payments.domain.vo.PaymentId;
import org.hzz.payments.domain.vo.PaymentStatus;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class PaymentProcessManagerImpl implements PaymentProcessManager, ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Override
    public CompletableFuture<Either<CommandFailure, Tuple2<PaymentId, PaymentStatus>>> process(
            PaymentCommand command) {
        Payment payment = new Payment(this.applicationContext);
        CompletableFuture<Either<CommandFailure, Event>> promise = payment.handle(command);

        promise.thenCompose(paymentRequested -> paymentRequested.fold(
                rejectPayment -> completed(rejectPayment),
                acceptPayment -> {}
        ))

        log.info("command:{}",command);
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private CompletableFuture<Either<CommandFailure, Tuple2<PaymentId, PaymentStatus>>> completed(CommandFailure rejectAuthorization) {
        return CompletableFuture.completedFuture(Either.left(rejectAuthorization));
    }

    private CompletableFuture<Either<CommandFailure, PaymentAuthorized>> authorize(Payment payment, PaymentRequested acceptPayment) {
        AuthorizePayment authorizePayment = AuthorizePayment.commandOf(
                acceptPayment.getPaymentId(),
                acceptPayment.getCustomerId(),
                acceptPayment.getPaymentMethod(),
                acceptPayment.getTransaction()
        );
        return payment.handle(authorizePayment);
    }
}
