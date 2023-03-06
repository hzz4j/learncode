package org.hzz.tl.inheritable;

public class InheritableThreadLocalDemo {
    private static  InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("Hello Q10Viking");
        new Thread(()->{
            System.out.println(threadLocal.get());
        }).start();
    }
}
/**
 * Hello Q10Viking
 */