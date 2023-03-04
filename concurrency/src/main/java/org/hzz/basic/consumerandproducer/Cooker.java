package org.hzz.basic.consumerandproducer;

public class Cooker implements Runnable{

    @Override
    public void run() {
        try{
            synchronized (Desk.lock){
                for (int i = 0; i<Desk.count;){
                    if(Desk.flag){
                        // 桌子上有汉堡，不生产
                        Desk.lock.wait();
                    }else{
                        // 生产
                        System.out.printf("%s 生产汉堡(%d)"+System.lineSeparator(),
                                Thread.currentThread().getName(),++i);
                        Desk.flag = true;
                        Desk.lock.notifyAll();
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("%s 结束工作"+System.lineSeparator(),Thread.currentThread().getName());
    }
}
