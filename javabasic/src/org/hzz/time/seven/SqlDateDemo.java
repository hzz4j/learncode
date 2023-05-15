package org.hzz.time.seven;

public class SqlDateDemo {
    public static void main(String[] args) {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        System.out.println(utilDate);
        System.out.println(sqlDate);
        System.out.println(utilDate.getTime() == sqlDate.getTime());
    }
}
/**
 * Mon May 15 14:13:33 GMT+08:00 2023
 * 2023-05-15
 * true
 */