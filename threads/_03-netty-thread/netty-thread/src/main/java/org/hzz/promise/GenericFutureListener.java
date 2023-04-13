package org.hzz.promise;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface GenericFutureListener<F extends Future<?>> {
    void operationComplete(F future) throws ExecutionException, InterruptedException;
}
