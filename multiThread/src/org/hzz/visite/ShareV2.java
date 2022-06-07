package org.hzz.visite;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间短证明
 */
public class ShareV2 {
    private static Logger logger = LoggerFactory.getLogger(ShareV2.class);
    //  共享变量
    private static boolean initFlag = false;

    public static void refresh(boolean v){
        printInfo();
        initFlag = v;
        printInfo();
    }

    public static void printInfo(){
        logger.info(Thread.currentThread().getName()+" see initFlag ->  "+initFlag);
    }

    public static void main(String[] args){
        Thread threadA = new Thread(()->{
            //  判断的时threadA工作内存的变量
            doBusi();
            // 确保线程ThreadB修改了initFlag
            Wait10Mills();
            doBusi();
        },"threadA");
        threadA.start();


        refresh(true);

        Thread threadB = new Thread(()->{
            refresh(false);
        },"threadB");
        threadB.start();

        Wait10Mills();
        Thread threadC = new Thread(()->{
            refresh(true);
        },"threadC");
        threadC.start();

    }

    public static void Wait10Mills(){
        try{
            Thread.sleep(10);
        }catch (Exception e){}
    }

    public static void doBusi(){
        printInfo();
        int counter=0;
        while (!initFlag){
            counter++;
        }
        printInfo();
    }
}
