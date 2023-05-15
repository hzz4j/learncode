package org.hzz.time.seven;

import java.util.Calendar;
import java.util.Date;

public class TimestampDemo {
    public static void main(String[] args) {
        long ts = new Date().getTime();
        System.out.println(ts);
        long ts2 = System.currentTimeMillis();
        System.out.println(ts2);
        long ts3 = Calendar.getInstance().getTimeInMillis();
        System.out.println(ts3);
        // java.sql.Date需要时间戳，而Java.util.Date不需要,直接返回当前时间
        java.sql.Date date = new java.sql.Date(ts);
        System.out.println(date);
    }
}
/**
 * 1684130747115
 * 1684130747115
 * 1684130747122
 * 2023-05-15
 */