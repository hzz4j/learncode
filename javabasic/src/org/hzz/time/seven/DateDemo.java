package org.hzz.time.seven;

import java.util.Calendar;
import java.util.Date;

public class DateDemo {
    public static void main(String[] args) {
        // 方式1
        Date date = new Date();
        System.out.println(date);
        // 方式2
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        System.out.println(time);
    }
}
/**
 * Mon May 15 13:40:48 GMT+08:00 2023
 * Mon May 15 13:40:48 GMT+08:00 2023
 */