package org.hzz.slidewindow;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class SlidingTimeWindowRateLimiter{
    private final int windowSize; // 时间窗口大小，单位：秒
    private final int maxRequests; // 时间窗口内允许的最大请求数
    private final Queue<Long> requestQueue; // 请求队列，保存请求的时间戳信息
    public SlidingTimeWindowRateLimiter(int windowSize, int maxRequests) {
        this.windowSize = windowSize;
        this.maxRequests = maxRequests;
        this.requestQueue = new LinkedList<>();
    }

    public synchronized boolean allowRequest() {
        // 当前时间的秒数
        long now = TimeUnit.MICROSECONDS.toSeconds(System.currentTimeMillis());
        // long now = System.currentTimeMillis() / 1000;
        while (!requestQueue.isEmpty() && now - requestQueue.peek() >= windowSize) {
            requestQueue.poll(); // 移除过期的请求
        }

        if (requestQueue.size() < maxRequests) {
            requestQueue.offer(now); // 将当前请求的时间戳加入队列
            return true; // 允许通过请求
        } else {
            return false; // 超过最大请求数，限流
        }
    }
}

