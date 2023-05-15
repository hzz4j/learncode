package org.hzz.time.eight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterDemo {
    public static void main(String[] args) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeFormat = dateTimeFormatter.format(LocalDateTime.now());
        System.out.println(timeFormat);

        LocalDateTime localDateTime = LocalDateTime.parse("2021-05-15 15:02:20", dateTimeFormatter);
        System.out.println(localDateTime);
    }
}
/**
 * 2023-05-15 15:45:21
 * 2021-05-15T15:02:20
 */
