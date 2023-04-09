package org.hzz.helper;

import java.time.LocalDateTime;

public class Const {
    public static int DEFAULT_PORT = 8080;
    public static String DEFAULT_SERVER_IP = "127.0.0.1";

    /*根据输入信息拼接出一个应答信息*/
    public static String response(String msg){
        return "Hello,"+msg+",Now is "+ LocalDateTime.now();
    }
}
