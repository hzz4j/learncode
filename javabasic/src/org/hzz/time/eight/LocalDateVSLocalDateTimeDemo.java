package org.hzz.time.eight;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateVSLocalDateTimeDemo {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        // LocalDate -> LocalDateTime
        LocalDateTime localDateTime = localDate.atStartOfDay();

        // LocalDateTime -> LocalDate
        localDate = localDateTime.toLocalDate();
    }
}
