package org.hzz.visite;

public class ShareV {
    private static boolean initFlag = false;
    public static void main(String[] args) throws Exception {
        Thread threadOne = new Thread(()->{
            System.out.println("线程1启动");
            int count=0;
            while(!initFlag){ count++; }
            System.out.printf("线程1跳出循环,执行了 %d次\n",count);
        });

        Thread threadTwo = new Thread(()->{
            System.out.println("线程2启动");
            initFlag = true;
        });
        threadOne.start();
        Thread.sleep(2);
        threadTwo.start();
    }
}


