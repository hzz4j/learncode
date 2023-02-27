package org.hzz.singleton.v3;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectAtack {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<InnerClassSingleton> constructor = InnerClassSingleton.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        // 通过发射InnerClassHolder的static静态代码块没有执行
        InnerClassSingleton innerClassSingleton = constructor.newInstance();
        InnerClassSingleton instance = InnerClassSingleton.getInstance();
        System.out.println(innerClassSingleton == instance); // false
    }
}
/**
 * Caused by: java.lang.RuntimeException: 单例不允许多个实例
 * 	at org.hzz.singleton.v3.InnerClassSingleton.<init>(InnerClassSingleton.java:13)
 * 	at org.hzz.singleton.v3.InnerClassSingleton.<init>(InnerClassSingleton.java:3)
 * 	at org.hzz.singleton.v3.InnerClassSingleton$InnerClassHolder.<clinit>(InnerClassSingleton.java:8)
 * 	... 6 more
 */