package org.hzz.time.eight;

import java.time.Duration;
import java.time.LocalDateTime;

public class DurationDemo {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime after = now.plusSeconds(53);
        long seconds = Duration.between(now, after).getSeconds();
        System.out.println(now);
        System.out.println(after);
        System.out.println(seconds); // 53
    }
}
/**
 * 2023-05-15T16:23:21.880
 * 2023-05-15T16:24:14.880
 * 53
 */