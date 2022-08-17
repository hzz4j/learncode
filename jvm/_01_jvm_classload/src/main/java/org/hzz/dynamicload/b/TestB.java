package org.hzz.dynamicload.b;

public class TestB {
    public static void main(String[] args) throws ClassNotFoundException {

        Class b0 = B.class;  // 懒加载，不会初始化
        // 懒加载，不会初始化B
        Class<?> b1 = Class.forName("org.hzz.dynamicload.b.B",false,TestB.class.getClassLoader());

        // Class.forName(className, true, currentLoader)
        // 等价于Class.forName("org.hzz.dynamicload.b.B",true,TestB.class.getClassLoader());
        // 会初始化
        Class<?> b2 = Class.forName("org.hzz.dynamicload.b.B");
        System.out.println(b0 == b1);
        System.out.println(b1 == b2);
    }
}

class B{
    static {
        System.out.println("B init success");
    }

    public static void hello() {
    }
}
/**
 * B init success
 * true
 * true
 */