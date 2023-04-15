package org.hzz.shutdown;

import org.hzz.promise.GenericFutureListener;

import java.util.concurrent.Future;

public interface FutureListener<V> extends GenericFutureListener<Future<V>> {
}
