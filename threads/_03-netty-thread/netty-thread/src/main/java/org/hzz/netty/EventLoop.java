package org.hzz.netty;

public interface EventLoop {
    void submit(Runnable task);
}
