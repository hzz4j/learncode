package org.hzz.completablefuture;

import java.util.concurrent.TimeUnit;

public class Utils {
    public static void sleepSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sleepRandomSeconds() {
        sleepSeconds((int) (Math.random() * 10));
    }
}
