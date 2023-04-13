package org.hzz.netty;

import java.util.concurrent.Executor;

public abstract class MultithreadEventLoopGroup  extends MultiThreadEventExecutorGroup implements EventLoopGroup{
    private static final int DEFAULT_EVENT_LOOP_THREADS;

    static {
        DEFAULT_EVENT_LOOP_THREADS = Math.max(1,  Runtime.getRuntime().availableProcessors() * 2);
    }

    public MultithreadEventLoopGroup(int nThreads) {
        super(nThreads == 0?DEFAULT_EVENT_LOOP_THREADS:nThreads);
    }

    @Override
    public EventLoop next() {
        return super.next();
    }

    //------------------测试的-------------------------------
    public void submit(Runnable task){
        EventLoop eventLoop = next();
        eventLoop.submit(task);
    }
}
