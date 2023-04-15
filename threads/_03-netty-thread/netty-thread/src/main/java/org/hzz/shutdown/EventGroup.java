package org.hzz.shutdown;

import org.hzz.promise.DefaultPromise;
import org.hzz.promise.Promise;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public class EventGroup implements ShutDown{
    private static final int MAX_CHILDREN;
    static{
        MAX_CHILDREN = Runtime.getRuntime().availableProcessors() * 2;
    }
    private final Executor executor;
    private EventChild[] childrens;

    private Promise<?> terminationFuture = new DefaultPromise<Void>();
    private AtomicInteger terminatedChildren = new AtomicInteger(0);

    public EventGroup(){
        this(MAX_CHILDREN);
    }

    public EventGroup(int maxChildren){
        maxChildren = maxChildren == 0 ? MAX_CHILDREN : maxChildren;
        executor = new MyExecutor();
        childrens = new EventChild[maxChildren];
        for (int i = 0; i < maxChildren; i++) {
            childrens[i] = new EventChild(executor,i);
        }

        // add shutdown listeners
        FutureListener<Object> listener = future -> {
            if (terminatedChildren.incrementAndGet() == childrens.length) {
                terminationFuture.setSuccess(null);
            }
        };

        for (EventChild children : childrens) {
            children.getTerminationFuture().addListener(listener);
        }
    }

    @Override
    public Promise<?> shutdownGracefully() {
        for (EventChild children : childrens) {
            children.shutdownGracefully();
        }
        return terminationFuture;
    }
}
