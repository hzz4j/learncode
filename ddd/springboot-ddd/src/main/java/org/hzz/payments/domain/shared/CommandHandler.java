package org.hzz.payments.domain.shared;

import io.vavr.control.Either;

import java.util.concurrent.CompletableFuture;

public interface CommandHandler<C extends Command,E extends Event,ID> {
    CompletableFuture<Either<CommandFailure,E>> handle(C command, ID entityId);
}
