package org.hzz.promise;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@Slf4j
public class TestPromiseDemo {
    private static final Executor executor = (r) -> {
        Thread thread = new Thread(r,"promise-test");
        thread.start();
    };
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Promise<String> promise = runTask();
        promise.addListener(future -> log.info("listener is done: {}",future.get()));
        promise.addListener(future -> log.info("Now,{}", LocalDateTime.now()));
        promise.sync();
        // 测试promise有结果后直接执行listener
        promise.addListener(future -> log.info("listener is done: {}",future.get()));
        log.info("main is done: {}",promise.get());
    }

    private static Promise<String> runTask(){
        DefaultPromise<String> promise = new DefaultPromise<>();
        executor.execute(getCommand(promise));
        return promise;
    }

    private static Runnable getCommand(DefaultPromise<String> promise) {
        return () -> {
            try {
                Thread.sleep(1000);
                promise.setSuccess("hello");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }
}
/**
 * 22:48:23.452 [main] INFO org.hzz.promise.TestPromiseDemo - listener is done: hello
 * 22:48:23.452 [promise-test] INFO org.hzz.promise.TestPromiseDemo - listener is done: hello
 * 22:48:23.458 [main] INFO org.hzz.promise.TestPromiseDemo - main is done: hello
 * 22:48:23.464 [promise-test] INFO org.hzz.promise.TestPromiseDemo - Now,2023-04-13T22:48:23.464
 */
