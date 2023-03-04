package org.hzz.basic.threethreadprint;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    private Semaphore semaphoreA = new Semaphore(1);
    private Semaphore semaphoreB = new Semaphore(0);
    private Semaphore semaphoreC = new Semaphore(0);
    private final static char[] ABC = {'A','B','C'};
    public static void main(String[] args) {
        SemaphoreDemo demo = new SemaphoreDemo();
        demo.new Task(0,demo.semaphoreA,demo.semaphoreB).start();
        demo.new Task(1,demo.semaphoreB,demo.semaphoreC).start();
        demo.new Task(2,demo.semaphoreC,demo.semaphoreA).start();

    }

    class Task extends Thread{
        private int threadId;
        private Semaphore self,next;

        public Task(int threadId,Semaphore self,Semaphore next){
            this.threadId = threadId;
            this.self = self;
            this.next = next;
        }

        private void print(){
            System.out.printf("%s print %s"+System.lineSeparator(),
                    Thread.currentThread().getName(),ABC[threadId]);
        }
        @Override
        public void run() {
            try {
                for (int i = 0; i < 3; i++) {
                    self.acquire(); // 为0时将无法继续获得该信号量
                    print();
                    next.release(); // 释放信号量加1
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
/**
 * Thread-0 print A
 * Thread-1 print B
 * Thread-2 print C
 * Thread-0 print A
 * Thread-1 print B
 * Thread-2 print C
 * Thread-0 print A
 * Thread-1 print B
 * Thread-2 print C
 */