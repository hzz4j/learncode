package org.hzz.singleton.v1;

public class LazySingletonTest {
    public static void main(String[] args) {

        Thread t1 = new Thread(()->{
            LazySingleton instance = LazySingleton.getInstance();
            System.out.println(instance);
        });

        Thread t2 = new Thread(()->{
            LazySingleton instance = LazySingleton.getInstance();
            System.out.println(instance);
        });

        t1.start();
        t2.start();

    }
}
/**
 * org.hzz.singleton.v1.LazySingleton@4a10f8eb
 * org.hzz.singleton.v1.LazySingleton@4a10f8eb
 */