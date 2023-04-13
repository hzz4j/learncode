package org.hzz.netty;

import lombok.extern.slf4j.Slf4j;
import org.hzz.factory.DefaultThreadFactory;
import org.hzz.netty.chooser.DefaultEventExecutorChooserFactory;
import org.hzz.netty.chooser.EventExecutorChooserFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

@Slf4j
public abstract class MultiThreadEventExecutorGroup {
    private  EventLoop[] children;
    private volatile EventExecutorChooserFactory.EventExecutorChooser chooser;
    private final Executor executor;


    protected MultiThreadEventExecutorGroup(int nThreads) {
        this(nThreads, DefaultEventExecutorChooserFactory.INSTANCE);
    }

    protected MultiThreadEventExecutorGroup(int nThreads, EventExecutorChooserFactory chooserFactory) {
        children = new EventLoop[nThreads];
        executor = new ThreadPerTaskExecutor(newDefaultFactory());

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


    public synchronized void addNThreads(int nThreads){
        int total = children.length + nThreads;
        EventLoop[] tmp  = new EventLoop[total];
        int i = 0;
        for (; i < children.length; i++) {
            tmp[i] = children[i];
        }

        for (;i<total;i++){
            tmp[i] = newChild(executor);
        }
        children = tmp;
        chooser = DefaultEventExecutorChooserFactory.INSTANCE.newChooser(children);
        log.info("add {} threads, total threads:{}",nThreads,children.length);
    }
}
