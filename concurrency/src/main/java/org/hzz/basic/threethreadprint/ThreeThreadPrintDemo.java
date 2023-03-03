package org.hzz.basic.threethreadprint;

public class ThreeThreadPrintDemo {
    private volatile int count = 1;
    private volatile int threadIdToRun = 0;
    private Object lock = new Object();
    private final static char[] ABC = {'A','B','C'};
    public static void main(String[] args) {
        ThreeThreadPrintDemo demo = new ThreeThreadPrintDemo();
        new Thread(demo.new Printer(0)).start();
        new Thread(demo.new Printer(1)).start();
        new Thread(demo.new Printer(2)).start();

    }

     class Printer implements Runnable{
        private int threadId;
        public Printer(int id){
            this.threadId = id;
        }
        @Override
        public void run() {
            try{
                while(count<10){
                    synchronized (lock){
                        if(threadId != threadIdToRun){
                            lock.wait();
                        }else{
                            System.out.printf("Thread-%d: Print: %s\n",threadId,ABC[threadId]);
                            count++;
                            // update threadIdToRun
                            threadIdToRun = (threadIdToRun + 1) % 3;
                            lock.notifyAll();  // 唤醒线程竞争锁
                            /** 等价 threadIdToRun = (threadIdToRun + 1) % 3;
                                if(threadIdToRun == 0){
                                    threadIdToRun = 1;
                                }else if (threadIdToRun == 1){
                                    threadIdToRun = 2;
                                }else if(threadIdToRun == 2){
                                    threadIdToRun = 0;
                                }
                             */
                        }
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
/**
 * Thread-0: Print: A
 * Thread-1: Print: B
 * Thread-2: Print: C
 * Thread-0: Print: A
 * Thread-1: Print: B
 * Thread-2: Print: C
 * Thread-0: Print: A
 * Thread-1: Print: B
 * Thread-2: Print: C
 */