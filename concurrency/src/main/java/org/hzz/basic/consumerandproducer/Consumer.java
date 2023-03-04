package org.hzz.basic.consumerandproducer;

public class Consumer implements Runnable {
    @Override
    public void run() {
        try {
            synchronized (Desk.lock){
                for (int i = 0; i<Desk.count;){
                    if(Desk.flag){
                        System.out.printf("%s 吃掉汉堡(%d)"+System.lineSeparator(),
                                Thread.currentThread().getName(),++i);
                        Desk.flag = false;
                        Desk.lock.notifyAll();
                    }else{
                        Desk.lock.wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("%s 离开餐厅"+System.lineSeparator(),Thread.currentThread().getName());
    }
}
