package org.hzz.time.eight;

import java.sql.Timestamp;
import java.time.*;

public class TimeStampToLocalDateDemo {
    public static void main(String[] args) {

        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong("1684080000000")),
                ZoneId.systemDefault());
        LocalDate localDate = localDateTime.toLocalDate();

        System.out.println(localDate);
        System.out.println(localDateTime);
    }
}
/**
 * 2023-05-15
 * 2023-05-15T00:00
 */
