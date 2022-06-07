package org.hzz.visite;

public class Jmm03_CodeVisibility {
    //  共享变量
    private static boolean initFlag = false;

    public static void refresh(){
        System.out.println(initFlag);
        initFlag = true;
        System.out.println(initFlag);
    }

    public static void main(String[] args){
        Thread threadA = new Thread(()->{
            //  判断的时threadA工作内存的变量
            System.out.println("A: "+initFlag);
            int counter=0;
            while (!initFlag){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter++;
            }
            System.out.println("A: "+initFlag);
            System.out.println(counter);
        },"threadA");
        threadA.start();
          // 现在的代码是可见的，但是如果加上这段代码就不可见了，
         // 主要是现在线程运行太快了
//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Thread threadB = new Thread(()->{
            refresh();
        },"threadB");
        threadB.start();
    }
}
