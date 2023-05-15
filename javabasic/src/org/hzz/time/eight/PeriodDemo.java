package org.hzz.time.eight;

import java.time.LocalDate;
import java.time.Period;

public class PeriodDemo {
    public static void main(String[] args) {
        LocalDate d1 = LocalDate.now();
        LocalDate d2 = d1.plusDays(2);
        Period period = Period.between(d1, d2);
        System.out.println(period.getDays()); // 2
    }
}
