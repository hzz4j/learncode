package org.hzz.tl;

public class UseThreadLocal {
    static ThreadLocal<String> threadLocal1 = new ThreadLocal<>();
    static ThreadLocal<Integer> threadLocal2 = new ThreadLocal<>();

    static class TestThread extends Thread{
        int id;
        public TestThread(int id){this.id = id;}
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            threadLocal1.set("线程__"+threadName);
            if(id == 2){
                threadLocal2.set(id);
            }
            System.out.println(threadName+":"+threadLocal1.get());
            System.out.println(threadName+":"+threadLocal2.get());
        }
    }

    private void startThreadArray(){
        for(int i = 0;i < 3 ; i++){
            new TestThread(i).start();
        }
    }
    public static void main(String[] args) {
        UseThreadLocal useThreadLocal = new UseThreadLocal();
        useThreadLocal.startThreadArray();
    }
}
/**
 * Thread-0:线程__Thread-0
 * Thread-0:null
 * Thread-2:线程__Thread-2
 * Thread-1:线程__Thread-1
 * Thread-1:null
 * Thread-2:2
 */