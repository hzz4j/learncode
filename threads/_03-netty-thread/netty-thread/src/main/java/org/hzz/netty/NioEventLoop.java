package org.hzz.netty;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class NioEventLoop extends SingleThreadEventLoop{

    private static final AtomicInteger eventId = new AtomicInteger();
    private int id;

    public NioEventLoop(Executor executor) {
        super(executor);
        this.id = eventId.incrementAndGet();
    }

    @Override
    protected void run() {
        for(;;){
            runAllTask();
        }
    }

    @Override
    public String toString() {
        return "NioEventLoop{" +
                "id=" + id +
                '}';
    }
}
