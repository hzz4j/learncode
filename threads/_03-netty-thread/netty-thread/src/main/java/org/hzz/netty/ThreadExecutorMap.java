package org.hzz.netty;

import java.util.concurrent.Executor;

public final class ThreadExecutorMap {
    private static final ThreadLocal<EventExecutor> executorThreadLocal = new ThreadLocal<>();

    private static void setCurrentEventExecutor(EventExecutor eventExecutor){
        executorThreadLocal.set(eventExecutor);
    }

    public static EventExecutor currentEventExecutor(){
        return executorThreadLocal.get();
    }

    public static Executor apply(Executor executor,EventExecutor eventExecutor){
         return (command) -> {
             executor.execute(apply(command,eventExecutor));
         };
    }

    private static Runnable apply(Runnable command,EventExecutor eventExecutor){
        return () -> {
            executorThreadLocal.set(eventExecutor);
            try {
                command.run();
            }finally {
                executorThreadLocal.remove();
            }
        };
    }
}
