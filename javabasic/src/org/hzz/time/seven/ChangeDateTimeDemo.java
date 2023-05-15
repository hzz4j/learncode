package org.hzz.time.seven;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeDateTimeDemo {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 从字符串转换为日期
        Date date1 = sdf.parse("2023-05-15 14:27:40");
        // 从日期转换为字符串
        String result = sdf.format(date1);
        System.out.println(result);

        System.out.println("====================================");

        long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        // 从时间戳转换为日期
        Date date2 = new Date(timestamp);
        System.out.println(sdf.format(date2));

        Timestamp timestamp2 = new Timestamp(timestamp);
        System.out.println(sdf.format(timestamp2));

    }
}
/**
 * 2023-05-15 14:27:40
 * ====================================
 * 1684132587075
 * 2023-05-15 14:36:27
 * 2023-05-15 14:36:27
 */