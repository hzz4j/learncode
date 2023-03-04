package org.hzz.basic.threethreadprint;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockConditionDemo {
    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();
    private volatile int threadIdtoRun = 0;
    private final static char[] ABC = {'A','B','C'};
    public static void main(String[] args) {
        ReentrantLockConditionDemo demo = new ReentrantLockConditionDemo();
        demo.new Task(0, demo.conditionA, demo.conditionB).start();
        demo.new Task(1, demo.conditionB, demo.conditionC).start();
        demo.new Task(2, demo.conditionC, demo.conditionA).start();
    }

    private void updateThreadIdtoRun(){
        threadIdtoRun = (threadIdtoRun + 1) % 3;
    }

    public class Task extends Thread{
        private int threadId;
        private Condition self,next;
        public Task(int threadId,Condition self,Condition next){
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
            try{
                lock.lock();
                for(int i = 0;i < 3; i++){
                    while(threadId != threadIdtoRun) // 自旋
                        self.await();  // 释放锁
                    print();
                    updateThreadIdtoRun();
                    next.signal();  // 唤醒锁
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
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