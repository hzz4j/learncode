package org.hzz.netty;

import org.hzz.factory.DefaultThreadFactory;
import org.hzz.netty.chooser.DefaultEventExecutorChooserFactory;
import org.hzz.netty.chooser.EventExecutorChooserFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

public abstract class MultiThreadEventExecutorGroup {
    private final EventLoop[] children;
    private final EventExecutorChooserFactory.EventExecutorChooser chooser;

    protected MultiThreadEventExecutorGroup(int nThreads) {
        this(nThreads, DefaultEventExecutorChooserFactory.INSTANCE);
    }

    protected MultiThreadEventExecutorGroup(int nThreads, EventExecutorChooserFactory chooserFactory) {
        children = new EventLoop[nThreads];
        Executor executor = new ThreadPerTaskExecutor(newDefaultFactory());
        for (int i = 0; i < nThreads; i++) {
            children[i] = newChild(executor);
        }
        chooser = chooserFactory.newChooser(children);
    }

    protected abstract EventLoop newChild(Executor executor);

    public EventLoop next() {
        return chooser.next();
    }

    protected ThreadFactory newDefaultFactory() {
        return new DefaultThreadFactory(getClass());
    }
}
