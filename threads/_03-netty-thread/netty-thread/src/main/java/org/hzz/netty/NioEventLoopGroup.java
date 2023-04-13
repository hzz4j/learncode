package org.hzz.netty;

import java.util.concurrent.Executor;

public class NioEventLoopGroup extends MultithreadEventLoopGroup{
    public NioEventLoopGroup(){
        this(0);
    }
    public NioEventLoopGroup(int nThreads) {
        super(nThreads);
    }

    @Override
    protected EventLoop newChild(Executor executor) {
        return new NioEventLoop(executor);
    }

}
