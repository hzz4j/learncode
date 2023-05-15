package org.hzz.collection.map.enummap;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class EnumMapTest {
    public static void main(String[] args) {
        test1();
        test2();
    }

    private static void test2(){
        Map<Integer,String> map = new HashMap<>();
        map.put(DayOfWeekConsts.MONDAY,"星期一");
        map.put(DayOfWeekConsts.TUESDAY, "星期二");
        map.put(DayOfWeekConsts.WEDNESDAY, "星期三");
        map.put(DayOfWeekConsts.THURSDAY, "星期四");
        map.put(DayOfWeekConsts.FRIDAY, "星期五");
        map.put(DayOfWeekConsts.SATURDAY, "星期六");
        map.put(DayOfWeekConsts.SUNDAY, "星期日");
        System.out.println(map);
        System.out.println(map.get(DayOfWeekConsts.MONDAY));
    }
    /**
     * {0=星期一, 1=星期二, 2=星期三, 3=星期四, 4=星期五, 5=星期六, 6=星期日}
     * 星期一
     */

    private static void test1(){
        Map<DayOfWeek,String> map = new EnumMap<>(DayOfWeek.class);
        map.put(DayOfWeek.MONDAY,"星期一");
        map.put(DayOfWeek.TUESDAY, "星期二");
        map.put(DayOfWeek.WEDNESDAY, "星期三");
        map.put(DayOfWeek.THURSDAY, "星期四");
        map.put(DayOfWeek.FRIDAY, "星期五");
        map.put(DayOfWeek.SATURDAY, "星期六");
        map.put(DayOfWeek.SUNDAY, "星期日");
        System.out.println(map);
        System.out.println(map.get(DayOfWeek.MONDAY));
    }
}
/**
 * {MONDAY=星期一, TUESDAY=星期二, WEDNESDAY=星期三, THURSDAY=星期四, FRIDAY=星期五, SATURDAY=星期六, SUNDAY=星期日}
 * 星期一
 */