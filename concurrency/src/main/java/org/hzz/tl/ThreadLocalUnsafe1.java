package org.hzz.tl;

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalUnsafe1 {
//    private static Number number = new Number(0);
    private static ThreadLocal<Number> threadLocal = new ThreadLocal(){
        // 初始化
        @Override
        protected Object initialValue() {
            return new Number(0);
        }
    };

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                ThreadLocalRandom current = ThreadLocalRandom.current();
                // 初始化
                Number number = threadLocal.get();
                number.setNumber(current.nextInt(100));
                threadLocal.set(number);
                try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.printf("%s = %s"+System.lineSeparator(),
                        Thread.currentThread().getName(),threadLocal.get());
            }).start();
        }
    }
}
/**
 * Thread-1 = Number{number=83}
 * Thread-3 = Number{number=76}
 * Thread-2 = Number{number=28}
 * Thread-4 = Number{number=25}
 * Thread-0 = Number{number=33}
 */