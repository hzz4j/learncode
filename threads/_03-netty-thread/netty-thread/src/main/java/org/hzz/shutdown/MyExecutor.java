package org.hzz.shutdown;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public class MyExecutor implements Executor {
    private AtomicInteger idx = new AtomicInteger(0);
    @Override
    public void execute(Runnable command) {
        new Thread(command,"hzz-thread-"+idx.getAndIncrement()).start();
    }
}
