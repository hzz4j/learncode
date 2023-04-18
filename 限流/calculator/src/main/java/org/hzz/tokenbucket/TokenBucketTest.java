package org.hzz.tokenbucket;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TokenBucketTest {
    private static final int THREAD_COUNT = 10; // 测试线程数
    private static final int REQUESTS_PER_THREAD = 100; // 每个线程发送的请求数量
    private static final long REQUEST_INTERVAL_MS = 50; // 请求间隔时间（毫秒）
    private static final int CAPACITY = 100; // 令牌桶容量
    private static final double RATE = 10; // 令牌生成速率


    public static void main(String[] args) {
        TokenBucket tokenBucket = new TokenBucket(CAPACITY, RATE);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        // 创建多个测试线程
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < REQUESTS_PER_THREAD; j++) {
                    // 模拟发送请求
                    if (tokenBucket.getToken()) {
                        log.info("Thread {} - Request {}: Got token", Thread.currentThread().getId(), (j + 1));
                    } else {
                        log.info("Thread {} - Request {}: No token", Thread.currentThread().getId(), (j + 1));
                    }

                    try {
                        Thread.sleep(REQUEST_INTERVAL_MS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/**
 * 20:26:45.546 [pool-1-thread-2] INFO org.hzz.tokenbucket.TokenBucketTest - Thread 13 - Request 10: Got token
 * 20:26:45.546 [pool-1-thread-8] INFO org.hzz.tokenbucket.TokenBucketTest - Thread 19 - Request 10: Got token
 * 20:26:45.610 [pool-1-thread-10] INFO org.hzz.tokenbucket.TokenBucketTest - Thread 21 - Request 11: No token
 * 20:26:45.610 [pool-1-thread-9] INFO org.hzz.tokenbucket.TokenBucketTest - Thread 20 - Request 11: Got token
 * 20:26:45.610 [pool-1-thread-5] INFO org.hzz.tokenbucket.TokenBucketTest - Thread 16 - Request 11: No token
 * 20:26:45.610 [pool-1-thread-3] INFO org.hzz.tokenbucket.TokenBucketTest - Thread 14 - Request 11: No token
 * 20:26:45.610 [pool-1-thread-7] INFO org.hzz.tokenbucket.TokenBucketTest - Thread 18 - Request 11: Got token
 * 20:26:45.610 [pool-1-thread-8] INFO org.hzz.tokenbucket.TokenBucketTest - Thread 19 - Request 11: Got token
 * 20:26:45.610 [pool-1-thread-1] INFO org.hzz.tokenbucket.TokenBucketTest - Thread 12 - Request 11: No token
 */