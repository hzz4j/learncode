package org.hzz.dynamicload.a;

/**
 * 类A并没有被加载
 */
public class TestA {
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = A.class.getClassLoader();
        System.out.println(A.class.getName());
        // 类加载 是一个懒加载，只有使用到了才会进行加载
        Class<?> aClass = classLoader.loadClass("org.hzz.dynamicload.a.A");
        System.out.println(A.class == aClass);
        System.out.println(A.class);
        // 初始化
        //aClass.newInstance();
        // A.hello();
    }
}

/**
 * org.hzz.dynamicload.a.A
 * true
 * class org.hzz.dynamicload.a.A
 */
class A {
    static {
        System.out.println("A init success");
    }

    public static void hello() {
    }
}