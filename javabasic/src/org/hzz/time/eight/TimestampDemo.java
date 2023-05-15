package org.hzz.time.eight;

import java.time.Instant;
public class TimestampDemo {
    public static void main(String[] args) {
        long epochMilli = Instant.now().toEpochMilli();
        long epochSecond = Instant.now().getEpochSecond();
        System.out.println(System.currentTimeMillis());
        System.out.println(epochMilli);
        System.out.println(epochSecond);

    }
}
/**
 * 1684134564173
 * 1684134564173
 * 1684134564
 */
