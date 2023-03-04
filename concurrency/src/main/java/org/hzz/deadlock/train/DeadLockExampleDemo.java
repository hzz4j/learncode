package org.hzz.deadlock.train;

public class DeadLockExampleDemo {

    public NamedLock resourceA = new NamedLock("resourceA");
    public NamedLock resourceB = new NamedLock("resourceB");

    public static void main(String[] args) {
        DeadLockExampleDemo demo = new DeadLockExampleDemo();
        new Thread(demo.new Task(demo.resourceA, demo.resourceB),"Thread-a").start();
        new Thread(demo.new Task(demo.resourceB, demo.resourceA),"Thread-b").start();
    }


    class NamedLock{
        public String name;
        public NamedLock(String name){
            this.name = name;
        }
    }
    class Task implements Runnable{
        private NamedLock prev;
        private NamedLock self;
        public Task(NamedLock prev,NamedLock self){
            this.prev = prev;
            this.self = self;
        }

        @Override
        public void run() {
            synchronized(prev){
                System.out.printf("[INFO]: %s get %s" + System.lineSeparator(),
                        Thread.currentThread().getName(),prev.name);

                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.printf("[INFO]: %s trying to get %s" + System.lineSeparator(),
                        Thread.currentThread().getName(),self.name);

                synchronized (self){
                    System.out.printf("[INFO]: %s get %s" + System.lineSeparator(),
                            Thread.currentThread().getName(),prev.name);
                }

                System.out.printf("[INFO]: %s finished" + System.lineSeparator(),
                        Thread.currentThread().getName());
            }
        }
    }

}
/**
 * [INFO]: Thread-a get resourceA
 * [INFO]: Thread-b get resourceB
 * [INFO]: Thread-a trying to get resourceB
 * [INFO]: Thread-b trying to get resourceA
 * ... 然后进入无尽的等待
 */