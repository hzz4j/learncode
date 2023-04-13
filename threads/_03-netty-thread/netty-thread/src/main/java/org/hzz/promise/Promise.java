package org.hzz.promise;

import java.util.concurrent.Future;

public interface Promise<V> extends Future<V> {

    Promise<V> setSuccess(V result);

    /**
     * Marks this future as a success and notifies all listeners.
     */
    boolean trySuccess(V result);

    Promise<V> sync() throws InterruptedException;

    void addListener(GenericFutureListener<? extends Future<? super V>> listener);

}
