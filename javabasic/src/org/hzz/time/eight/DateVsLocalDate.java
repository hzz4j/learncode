package org.hzz.time.eight;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

public class DateVsLocalDate {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        Date date = new Date(
                localDate.getYear() - 1900,
                localDate.getMonthValue() - 1,
                localDate.getDayOfMonth()
        );

        // 通过时间戳
        Date date1 = new Date(Timestamp.valueOf(localDate.atStartOfDay()).getTime());

        System.out.println(date);
        System.out.println(date1);
    }
}
/**
 * Mon May 15 00:00:00 GMT+08:00 2023
 * Mon May 15 00:00:00 GMT+08:00 2023
 */