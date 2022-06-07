package org.hzz.visite;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间短证明
 */
public class ShareV4 {
    private static Logger logger = LoggerFactory.getLogger(ShareV4.class);
    //  共享变量
    private static boolean initFlag = false;

    public static void main(String[] args){

        Thread threadA = new Thread(()->{
            logger.info("线程A启动，此时 initFlag = "+initFlag);
            int counter=0;
            while (!initFlag){
//                waitofMillis(5);
                counter++;
            }
            logger.info("线程A循环"+counter+"次后，退出，此时initFlag = "+initFlag);
        });
        threadA.start();


        waitofMillis(10);
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

    public static void printInfo(){
        logger.info(Thread.currentThread().getName()+" see initFlag ->  "+initFlag);
    }
}
