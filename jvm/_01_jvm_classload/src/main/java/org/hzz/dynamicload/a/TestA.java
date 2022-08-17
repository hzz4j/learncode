package org.hzz.dynamicload.a;

/**
 * 类A并没有被加载
 */
public class TestA {
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = A.class.getClassLoader();
        // 类加载 是一个懒加载，只有使用到了才会进行加载
        Class<?> aClass = classLoader.loadClass("org.hzz.dynamicload.A");
        // 初始化
        //aClass.newInstance();
        // A.hello();
    }
}
class A {
    static {
        System.out.println("hello World");
    }

    public static void hello() {
    }
}