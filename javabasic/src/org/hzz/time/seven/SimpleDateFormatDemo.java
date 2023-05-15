package org.hzz.time.seven;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatDemo {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String result = sdf.format(date);
        System.out.println(result);
        // 从字符串转换为日期
        date = sdf.parse("2023-05-15 14:26:12");
        System.out.println(date);
    }
}
/**
 * 2023-05-15 14:27:40
 * Mon May 15 14:26:12 GMT+08:00 2023
 */