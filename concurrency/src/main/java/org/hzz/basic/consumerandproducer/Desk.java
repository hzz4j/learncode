package org.hzz.basic.consumerandproducer;

public class Desk {
    public static volatile int count = 5;
    // 桌子上是否有汉堡
    public static volatile boolean flag = false;
    public final static Object lock = new Object();

    public static void main(String[] args) {
        new Thread(new Cooker(),"厨师").start();
        new Thread(new Consumer(),"Q10Viking").start();
    }
}
/**
 * 厨师 生产汉堡(1)
 * Q10Viking 吃掉汉堡(1)
 * 厨师 生产汉堡(2)
 * Q10Viking 吃掉汉堡(2)
 * 厨师 生产汉堡(3)
 * Q10Viking 吃掉汉堡(3)
 * 厨师 生产汉堡(4)
 * Q10Viking 吃掉汉堡(4)
 * 厨师 生产汉堡(5)
 * 厨师 结束工作
 * Q10Viking 吃掉汉堡(5)
 * Q10Viking 离开餐厅
 */