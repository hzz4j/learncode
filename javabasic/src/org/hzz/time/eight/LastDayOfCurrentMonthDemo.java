package org.hzz.time.eight;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class LastDayOfCurrentMonthDemo {
    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        LocalDate result = now.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(result); // 2023-05-31
    }
}
