package org.hzz.netty.chooser;

import org.hzz.netty.EventExecutor;
import org.hzz.netty.EventLoop;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 选择EventExcutor的工厂类
 */
public class DefaultEventExecutorChooserFactory implements EventExecutorChooserFactory {
    public static final DefaultEventExecutorChooserFactory INSTANCE = new DefaultEventExecutorChooserFactory();

    private DefaultEventExecutorChooserFactory(){}
    @Override
    public EventExecutorChooser newChooser(EventLoop[] executors) {
        return new GenericEventExecutorChooser(executors);
    }

    private class GenericEventExecutorChooser implements EventExecutorChooser {
        
        private final AtomicInteger idx = new AtomicInteger();
        private EventLoop[] executors;
        GenericEventExecutorChooser(EventLoop[] executors) {
            this.executors = executors;
        }

        @Override
        public EventLoop next() {
            return executors[Math.abs(idx.getAndIncrement() % executors.length)];
        }
    }
}
