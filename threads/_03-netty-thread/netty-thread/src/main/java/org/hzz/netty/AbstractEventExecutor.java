package org.hzz.netty;

public abstract class AbstractEventExecutor implements EventExecutor{
    @Override
    public boolean inEventLoop() {
        return inEventLoop(Thread.currentThread());
    }
}
