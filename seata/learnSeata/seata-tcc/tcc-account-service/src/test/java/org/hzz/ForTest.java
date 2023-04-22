package org.hzz;

import java.sql.Timestamp;
import java.util.Date;

public class ForTest {
    public static void main(String[] args) {
        String groupid = "goods-group";
        int r = Math.abs(groupid.hashCode())%50;
        System.out.println(r);

        // 18：59
        //Sat Apr 22 18:59:20 GMT+08:00 2023
        //Sat Apr 22 18:59:25 GMT+08:00 2023
        //Sat Apr 22 18:59:30 GMT+08:00 2023
        //Sat Apr 22 18:59:35 GMT+08:00 2023
        //Sat Apr 22 18:59:40 GMT+08:00 2023
        Timestamp stamp = new Timestamp(1682161160164L);
        Date date = new Date(stamp.getTime());
        System.out.println(date);


    }
}
