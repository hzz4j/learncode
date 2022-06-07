package org.hzz.tongxin.server.asyncpro;
import io.netty.util.NettyRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class AsyncBusiProcess {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncBusiProcess.class);
    private static BlockingQueue<Runnable> taskQueue  = new ArrayBlockingQueue<Runnable>(3000);

    private static ExecutorService executorService = new ThreadPoolExecutor(1,
            NettyRuntime.availableProcessors(),60, TimeUnit.SECONDS,taskQueue);

    public static void submitTask(Runnable task){
        executorService.execute(task);
    }
}
