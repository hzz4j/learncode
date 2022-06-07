package io.netty.util.concurrent.hzz.test;

import io.netty.util.concurrent.*;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


public class LearnPromise {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(LearnPromise.class);
    public static void main(String[] args) throws Exception {
        testListenerNotifyOrder();
    }

    public static void testListenerNotifyOrder() throws Exception {
        EventExecutor executor = new TestEventExecutor();
        try {
            final BlockingQueue<FutureListener<Void>> listeners = new LinkedBlockingQueue<FutureListener<Void>>();
            int runs = 100000;

            for (int i = 0; i < runs; i++) {
                final Promise<Void> promise = new DefaultPromise<Void>(executor);
                final FutureListener<Void> listener1 = new FutureListener<Void>() {
                    @Override
                    public void operationComplete(Future<Void> future) throws Exception {
//                        System.out.println("run listener1");
                        logger.info("run listener1");
                        listeners.add(this);
                    }
                };
                final FutureListener<Void> listener2 = new FutureListener<Void>() {
                    @Override
                    public void operationComplete(Future<Void> future) throws Exception {
//                        System.out.println("run listener2");
                        logger.info("run listener2");
                        listeners.add(this);
                    }
                };
//                GlobalEventExecutor.INSTANCE.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        promise.setSuccess(null);
//                    }
//                });

                Thread t = new Thread(()->{
                    try{
                        Thread.sleep(2000);
                        promise.setSuccess(null);
                    }catch (Exception x){}

                });
                t.start();

                promise.addListener(listener1).addListener(listener2);
            }
        } finally {
            executor.shutdownGracefully(0, 0, TimeUnit.SECONDS).sync();
        }
    }

    private static final class TestEventExecutor extends SingleThreadEventExecutor {
        TestEventExecutor() {
            super(null, Executors.defaultThreadFactory(), true);
        }

        @Override
        protected void run() {
            for (;;) {
                Runnable task = takeTask();
                if (task != null) {
                    task.run();
                    updateLastExecutionTime();
                }

                if (confirmShutdown()) {
                    break;
                }
            }
        }
    }
}
