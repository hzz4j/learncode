package org.hzz.variables;

public class TestFinal {
    // 静态常量  必须赋值
    // private static final int STAIC_FINAL_V;
    private static final int STAIC_FINAL_V = 1;

    // 成员常量
    // 可以在构造器中赋值
    private final int MEMBER_FINAL_V1;
    // 也可以直接赋值，总之常量在使用前，最终必须要赋值
    private final int MEMBER_FINAL_V2 = 1;
    public TestFinal(){
        // 编译不通过，常量在使用前必须赋值
        // System.out.println(MEMBER_FINAL_V1);
        MEMBER_FINAL_V1 = 1;
    }
    public static void main(String[] args) {

        // 局部常量
        // final static int LOCAL_FINAL = 1;  // 不能这么定义
        final int LOCAL_FINAL = 1;
    }
}
