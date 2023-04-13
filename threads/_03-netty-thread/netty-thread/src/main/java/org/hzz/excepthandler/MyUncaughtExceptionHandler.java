package org.hzz.excepthandler;

public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("线程"+t.getName()+"出现异常");
        e.printStackTrace();
    }

    public static void main(String[] args) {
        Thread thread = new Thread(()->{
            System.out.println(1/0);
        });
        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        thread.start();
    }
}
/**
 * 线程Thread-0出现异常
 * java.lang.ArithmeticException: / by zero
 * 	at org.hzz.excepthandler.MyUncaughtExceptionHandler.lambda$main$0(MyUncaughtExceptionHandler.java:12)
 * 	at java.lang.Thread.run(Thread.java:750)
 */