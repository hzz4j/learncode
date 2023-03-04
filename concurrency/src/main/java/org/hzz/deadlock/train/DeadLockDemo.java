package org.hzz.deadlock.train;

public class DeadLockDemo {
    public static void main(String[] args) {
        new Thread(TrainA::method,"train-a").start();
        new Thread(TrainB::method,"train-b").start();
    }

    static class TrainA{
        public static synchronized void method(){
            try {
                Thread.sleep(10 * 1000);
                TrainB.method();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class TrainB{
        public static synchronized void method(){
            try {
                Thread.sleep(10 * 1000);
                TrainA.method();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
