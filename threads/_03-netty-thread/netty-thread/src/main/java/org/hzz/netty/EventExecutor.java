package org.hzz.netty;

public interface EventExecutor {
    boolean inEventLoop();
    boolean inEventLoop(Thread thread);
}
