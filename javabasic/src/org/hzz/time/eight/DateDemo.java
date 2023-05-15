package org.hzz.time.eight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateDemo {
    public static void main(String[] args) {
        // 获取日期
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        // 获取时间
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);
        // 获取日期和时间
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
    }
}
/**
 * 2023-05-15
 * 15:02:20.216
 * 2023-05-15T15:02:20.217
 */