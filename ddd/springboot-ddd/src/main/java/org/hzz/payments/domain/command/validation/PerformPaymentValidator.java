package org.hzz.payments.domain.command.validation;

import io.vavr.control.Either;
import org.hzz.payments.domain.command.PerformPayment;
import org.hzz.payments.domain.shared.CommandFailure;
import org.hzz.payments.domain.shared.CommandValidation;
import org.springframework.stereotype.Component;

@Component
public class PerformPaymentValidator implements CommandValidation<PerformPayment> {
    @Override
    public Either<CommandFailure, PerformPayment> acceptOrReject(PerformPayment command) {
        return Either.right(command);
    }
}
