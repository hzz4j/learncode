package org.hzz;

public class TestB {
    public static void main(String[] args) throws ClassNotFoundException {
        Class b0 = B.class;  // 懒加载，不会初始化
        // 懒加载，不会初始化B
        Class<?> b1 = Class.forName("org.hzz.B",false,TestB.class.getClassLoader());
        System.out.println(b1 == b0); // true
        // 会初始化
        Class<?> b2 = Class.forName("org.hzz.B");
        System.out.println(b1 == b2); // true
    }
}
/**输出
 * true
 * B init success
 * true
 */
class B{
    static {
        System.out.println("B init success");
    }
}
