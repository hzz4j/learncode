package org.hzz.time.eight;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ChangeLocalDateToTimestamp {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
       // LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTime = localDate.atStartOfDay();

        // LocalDate转换为时间戳
        long timestamp1 = localDate.atStartOfDay().toInstant(java.time.ZoneOffset.ofHours(8)).toEpochMilli();
        long timestamp2 = Timestamp.valueOf(localDateTime).getTime(); // 接收的是一个LocalDateTime类型
        System.out.println(timestamp1);
        System.out.println(timestamp2);
        System.out.println(timestamp1 == timestamp2);
    }
}
/**
 * 1684080000000
 * 1684080000000
 * true
 */