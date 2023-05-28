package org.hzz.payments.application.impl;

import io.vavr.Tuple2;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.hzz.payments.application.PaymentProcessManager;
import org.hzz.payments.domain.command.PaymentCommand;
import org.hzz.payments.domain.shared.CommandFailure;
import org.hzz.payments.domain.vo.PaymentId;
import org.hzz.payments.domain.vo.PaymentStatus;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class PaymentProcessManagerImpl implements PaymentProcessManager {
    @Override
    public CompletableFuture<Either<CommandFailure, Tuple2<PaymentId, PaymentStatus>>> process(PaymentCommand command) {
        log.info("command:{}",command);
        return null;
    }
}
