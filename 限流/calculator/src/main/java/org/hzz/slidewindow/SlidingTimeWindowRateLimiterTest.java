package org.hzz.slidewindow;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlidingTimeWindowRateLimiterTest {
    public static void main(String[] args) {
        int windowSize = 5; // 时间窗口大小，单位：秒
        int maxRequests = 5; // 时间窗口内允许的最大请求数
        SlidingTimeWindowRateLimiter rateLimiter = new SlidingTimeWindowRateLimiter(windowSize, maxRequests);

        // 模拟请求
        for (int i = 1; i <= 15; i++) {
            int finalI = i;
            new Thread(() -> {
                boolean allowed = rateLimiter.allowRequest();
                log.info("请求" + finalI + " 是否允许通过: " + allowed);
                try {
                    Thread.sleep(500); // 每次请求间隔0.5秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
/**
 * 18:24:29.038 [Thread-11] INFO org.hzz.slidewindow.SlidingTimeWindowRateLimiterTest - 请求12 是否允许通过: true
 * 18:24:29.038 [Thread-12] INFO org.hzz.slidewindow.SlidingTimeWindowRateLimiterTest - 请求13 是否允许通过: true
 * 18:24:29.039 [Thread-2] INFO org.hzz.slidewindow.SlidingTimeWindowRateLimiterTest - 请求3 是否允许通过: false
 * 18:24:29.039 [Thread-3] INFO org.hzz.slidewindow.SlidingTimeWindowRateLimiterTest - 请求4 是否允许通过: false
 * 18:24:29.039 [Thread-9] INFO org.hzz.slidewindow.SlidingTimeWindowRateLimiterTest - 请求10 是否允许通过: true
 * 18:24:29.038 [Thread-13] INFO org.hzz.slidewindow.SlidingTimeWindowRateLimiterTest - 请求14 是否允许通过: true
 * 18:24:29.039 [Thread-1] INFO org.hzz.slidewindow.SlidingTimeWindowRateLimiterTest - 请求2 是否允许通过: false
 * 18:24:29.039 [Thread-5] INFO org.hzz.slidewindow.SlidingTimeWindowRateLimiterTest - 请求6 是否允许通过: false
 * 18:24:29.039 [Thread-4] INFO org.hzz.slidewindow.SlidingTimeWindowRateLimiterTest - 请求5 是否允许通过: false
 * 18:24:29.038 [Thread-6] INFO org.hzz.slidewindow.SlidingTimeWindowRateLimiterTest - 请求7 是否允许通过: true
 * 18:24:29.038 [Thread-10] INFO org.hzz.slidewindow.SlidingTimeWindowRateLimiterTest - 请求11 是否允许通过: true
 * 18:24:29.038 [Thread-14] INFO org.hzz.slidewindow.SlidingTimeWindowRateLimiterTest - 请求15 是否允许通过: true
 * 18:24:29.039 [Thread-7] INFO org.hzz.slidewindow.SlidingTimeWindowRateLimiterTest - 请求8 是否允许通过: true
 * 18:24:29.038 [Thread-0] INFO org.hzz.slidewindow.SlidingTimeWindowRateLimiterTest - 请求1 是否允许通过: true
 * 18:24:29.039 [Thread-8] INFO org.hzz.slidewindow.SlidingTimeWindowRateLimiterTest - 请求9 是否允许通过: true
 */