package org.hzz.calculator;

import java.util.concurrent.atomic.AtomicInteger;

public class Calculator {
    private final int limit; // 限制阈值
    private final long interval; // 时间窗口间隔
    private final AtomicInteger counter; // 计数器
    private volatile long lastResetTime; // 上次重置计数器的时间

    public Calculator(int limit, long interval) {
        this.limit = limit;
        this.interval = interval;
        this.counter = new AtomicInteger(0);
        this.lastResetTime = System.currentTimeMillis();
    }


    // 请求计数器加1
    public void increment() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastResetTime >= interval) {
            // 如果距离上次重置计数器的时间超过了时间窗口间隔，就重置计数器
            counter.set(0);
            lastResetTime = currentTime;
        }
        counter.incrementAndGet();
    }

    // 判断请求是否超过限制阈值
    public boolean isExceeded() {
        return counter.get() > limit;
    }

    public int getCounter() {
        return counter.get();
    }
}
