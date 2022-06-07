package org.hzz.visite;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 时间短证明
 */
public class ShareV7 {
    private static Logger logger = LoggerFactory.getLogger(ShareV7.class);
    //  共享变量
    private static boolean initFlag = false;

    public static void main(String[] args){
        Thread threadA = new Thread(()->{
            logger.info("线程A启动，此时 initFlag = "+initFlag);
            int counter=0;
            while (!initFlag){
                counter++;
            }
            logger.info("线程A循环"+counter+"次后，退出，此时initFlag = "+initFlag);
        },"threadA");
        threadA.start();

        waitofMillis(6);

        Thread threadB = new Thread(()->{
            logger.info("线程B启动，此时 initFlag = "+initFlag);
            initFlag = true;
            logger.info("线程B修改 此时 initFlag = "+initFlag);
        },"threadB");
        threadB.start();
    }

    public static void waitofMillis(long millis){
        try{
            Thread.sleep(millis);
        }catch (Exception e){}
    }

}
