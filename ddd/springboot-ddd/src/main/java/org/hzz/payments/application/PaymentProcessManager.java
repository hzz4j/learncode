package org.hzz.payments.application;

import io.vavr.Tuple1;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import org.hzz.payments.domain.command.PaymentCommand;
import org.hzz.payments.domain.shared.CommandFailure;
import org.hzz.payments.domain.vo.PaymentId;
import org.hzz.payments.domain.vo.PaymentStatus;

import java.util.concurrent.CompletableFuture;

public interface PaymentProcessManager {
    CompletableFuture<Either<CommandFailure, Tuple2<PaymentId, PaymentStatus>>> process(PaymentCommand command);
}

