package org.hzz.netty;

import java.util.concurrent.Executor;

public  abstract class SingleThreadEventLoop extends SingleThreadEventExecutor implements EventLoop{
    public SingleThreadEventLoop(Executor executor) {
        super(executor);
    }

    @Override
    public void submit(Runnable task) {
        super.execute(task);
    }
}
