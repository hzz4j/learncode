package org.hzz.basic.communicate;

public class VolatileDemoTest {
    private static volatile boolean flag;
    public static void main(String[] args) {
        new Thread(()->{
            while(true){
                if(!flag){
                    System.out.printf("%s turn on\n",Thread.currentThread().getName());
                    flag = true;
                }
            }
        },"t1").start();

        new Thread(()->{
            while(true){
                if(flag){
                    System.out.printf("%s turn off\n",Thread.currentThread().getName());
                    flag = false;
                }
            }
        },"t2").start();
    }
}
/**
 * ...
 * t1 turn on
 * t2 turn off
 * t1 turn on
 * t2 turn off
 * t1 turn on
 * t2 turn off
 * ...
 */