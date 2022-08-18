package org.hzz.variables;

public class TestConstantValue {
    public static void main(String[] args) {
        System.out.println(V.A);
        System.out.println(V.B); // 访问类中ConstantValue（字面常量并不会触发初始化）
        System.out.println("------------------------");
        //System.out.println(V.C);
    }

}

/**
 * 1
 * hello World
 * ------------------------
 */
class V{
    public final static int A = 1;
    public final static String B = "hello World";
    public final static String C = new String("hello World");

    static {
        System.out.println("init success");
    }
}
