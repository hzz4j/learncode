package org.hzz.variables;

public class TestMember {
    // 类变量(静态变量) 不赋值默认取0，boolean为false,引用对象为null
    private static int m1;
    // 实例变量
    private int m;


    // 常量
    // 静态常量
    private final static int M1 = 1;
    // 实例常量
    private final int M2;

    TestMember(){ this.M2 = 1;}
}
