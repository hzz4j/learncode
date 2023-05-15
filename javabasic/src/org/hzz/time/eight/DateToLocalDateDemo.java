package org.hzz.time.eight;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateToLocalDateDemo {
    public static void main(String[] args) {
        Date date = new Date();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(date.getTime()),
                ZoneId.systemDefault());
        LocalDate localDate = localDateTime.toLocalDate();

        System.out.println(date);
        System.out.println(localDateTime);
        System.out.println(localDate);
    }
}
/**
 * Mon May 15 15:40:28 GMT+08:00 2023
 * 2023-05-15T15:40:28.510
 * 2023-05-15
 */