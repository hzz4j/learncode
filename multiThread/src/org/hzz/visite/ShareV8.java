package org.hzz.visite;

public class ShareV8 {
    private static boolean initFlag = false;
    public static void main(String[] args) throws Exception {
        test();
    }

    public static void test() throws Exception{
        new Task(){
            @Override
            public void run() {
                System.out.println("线程1 开始");
                int count=0;
                while(!initFlag){
                   count++;
                }
                System.out.printf("线程1跳出循环,执行了 %d次\n",count);
            }
        };

        Thread.sleep(2);

        new Task(){
            @Override
            public void run() {
                System.out.println("线程2 开始");
                initFlag = true;
                System.out.println(initFlag);
            }
        };
    }

    /**
     * 模板方法
     */
    public static abstract class Task extends Thread{
        public Task(){
            start();
        }
        @Override
        public abstract void run();
    }
}


