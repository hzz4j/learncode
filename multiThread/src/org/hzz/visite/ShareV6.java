package org.hzz.visite;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间短证明
 */
public class ShareV6 {
    private static Logger logger = LoggerFactory.getLogger(ShareV6.class);
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



        Thread threadB = new Thread(()->{
            logger.info("线程B启动，此时 initFlag = "+initFlag);
            waitofMillis(3);

            Thread threadD = new Thread(()->{
                logger.info("线程D启动，此时 initFlag = "+initFlag);
                int counter=0;
                while (!initFlag){
                    counter++;
                }
                logger.info("线程D循环"+counter+"次后，退出，此时initFlag = "+initFlag);
            },"threadD");
            threadD.start();


            initFlag = true;
            logger.info("线程B修改 此时 initFlag = "+initFlag);
        },"threadB");
        threadB.start();


        Thread threadC = new Thread(()->{
            logger.info("线程C启动，此时 initFlag = "+initFlag);
            int counter=0;
            while (!initFlag){
                counter++;
            }
            logger.info("线程C循环"+counter+"次后，退出，此时initFlag = "+initFlag);
        },"threadC");
        threadC.start();


        waitofMillis(10);
        logger.info("main线程: "+initFlag);
    }

    public static void waitofMillis(long millis){
        try{
            Thread.sleep(millis);
        }catch (Exception e){}
    }

}
