package org.hzz.netty.chooser;

import org.hzz.netty.EventLoop;

public interface EventExecutorChooserFactory {

    EventExecutorChooser newChooser(EventLoop[] executors);

    interface EventExecutorChooser{
        EventLoop next();
    }
}
