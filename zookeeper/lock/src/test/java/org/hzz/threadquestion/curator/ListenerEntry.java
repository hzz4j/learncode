package org.hzz.threadquestion.curator;

import java.util.concurrent.Executor;

public class ListenerEntry<T> {
    public final T        listener;
    public final Executor executor;

    public ListenerEntry(T listener, Executor executor)
    {
        this.listener = listener;
        this.executor = executor;
    }
}
