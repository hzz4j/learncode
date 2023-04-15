package org.hzz.promise;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class DefaultPromise<V> implements Promise<V> {
    private static final Object SUCCESS = new Object();
    private static final AtomicReferenceFieldUpdater<DefaultPromise, Object> RESULT_UPDATER =
            AtomicReferenceFieldUpdater.newUpdater(DefaultPromise.class, Object.class, "result");
    private volatile Object result;
    private short waiters;
    private List<GenericFutureListener> listeners;

    @Override
    public Promise<V> setSuccess(Object result) {
        if (setSuccess0(result))
            return this;
        throw new IllegalStateException("complete already" + this);
    }


    private boolean setSuccess0(Object result) {
        return setValue0(result == null ? SUCCESS : result);
    }

    private boolean setValue0(Object result) {
        if (RESULT_UPDATER.compareAndSet(this, null, result)) {
            if (checkNotifyWaiters()) {
                notifyListeners();
            }
            return true;
        }
        return false;
    }


    private void notifyListeners() {
        for (GenericFutureListener listener : listeners) {
            notifyListener(listener);
        }
    }

    private void notifyListener(GenericFutureListener listener){
        try{
            listener.operationComplete(this);
        }catch (Throwable e){
        }

    }

    private synchronized boolean checkNotifyWaiters() {
        if (waiters > 0) {
            notifyAll();
        }
        return listeners != null;
    }


    @Override
    public boolean trySuccess(V result) {
        return setSuccess0(result);
    }

    @Override
    public Promise<V> sync() throws InterruptedException {
        if (isDone()) return this;
        await();
        return this;
    }

    @Override
    public void addListener(GenericFutureListener<? extends Future<? super V>> listener) {
        if (isDone()) {
            notifyListener(listener);
        } else {
            synchronized (this) {
                if (isDone()) {
                    notifyListener(listener);
                } else {
                    if (listeners == null) {
                        listeners = new ArrayList<>();
                    }
                    listeners.add(listener);
                }
            }
        }
    }

    public Promise<V> syncUninterruptibly() {
        if (isDone()) return this;
        awaitUninterruptibly();
        return this;
    }


    private void await() throws InterruptedException {
        synchronized (this) {
            while (!isDone()) {
                incWaiters();
                try {
                    wait();
                } finally {
                    decWaiters();
                }
            }
        }
    }

    private void awaitUninterruptibly() {
        boolean interrupted = false;
        try {
            synchronized (this) {
                while (!isDone()) {
                    incWaiters();
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        interrupted = true;
                    } finally {
                        decWaiters();
                    }
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private boolean await(long timeout, TimeUnit unit) throws InterruptedException {
        return await0(unit.toMillis(timeout), true);
    }

    /**
     * @param timeout
     * @param interruptable 是否允许中断异常抛出 为true那么wait过程中被中断就直接抛异常，否则继续等待直到时间耗尽
     * @return
     * @throws InterruptedException
     */
    private boolean await0(long timeout, boolean interruptable) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        long waitTime = timeout;

        if (interruptable && Thread.interrupted())
            throw new InterruptedException(toString());
        boolean interrupted = false;
        try {
            for (; ; ) {
                synchronized (this) {
                    if (isDone()) return true;
                    try {
                        incWaiters();
                        wait(waitTime);
                    } catch (InterruptedException e) {
                        if (interruptable) throw e;
                        else interruptable = true;
                    } finally {
                        decWaiters();
                    }
                }
                if (isDone()) {
                    return true;
                } else {
                    // 由于被唤醒或者中断需要继续
                    waitTime = timeout - (System.currentTimeMillis() - startTime);
                    if (waitTime <= 0) // 等待时间耗尽
                        return isDone();
                }

            }
        } finally {
            // 确保中断标志被恢复
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void incWaiters() {
        if (waiters == Short.MAX_VALUE) {
            throw new IllegalStateException("too many waiters: " + waiters);
        }
        ++waiters;
    }


    private void decWaiters() {
        --waiters;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return result != null;
    }

    @Override
    public V get() throws InterruptedException {
        if (!isDone()) {
            await();
        }
        if (result == SUCCESS) {
            return null;
        }
        return (V) result;
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
        if (!isDone()) {
            if (!await(timeout, unit)) { // 等待超时还没有获得结果
                throw new TimeoutException(toString());
            }
        }
        if (result == SUCCESS) {
            return null;
        }
        return (V) result;
    }


    @Override
    public String toString() {
        return toStringBuilder().toString();
    }

    private StringBuilder toStringBuilder() {
        StringBuilder buf = new StringBuilder(64)
                .append(this.getClass().getSimpleName())
                .append('@')
                .append(Integer.toHexString(hashCode()));

        if (result == SUCCESS) {
            buf.append("(success)");
        } else if (result != null) {
            buf.append("(success: ")
                    .append(result)
                    .append(')');
        } else {
            buf.append("(incomplete)");
        }
        return buf;
    }
}
