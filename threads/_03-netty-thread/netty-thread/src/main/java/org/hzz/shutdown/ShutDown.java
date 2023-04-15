package org.hzz.shutdown;

import org.hzz.promise.Promise;

public interface ShutDown {
    Promise<?> shutdownGracefully();
}
