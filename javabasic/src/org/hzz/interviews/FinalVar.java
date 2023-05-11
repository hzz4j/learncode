package org.hzz.interviews;

public class FinalVar {
    final static int a = 0; //在声明的时候就需要赋值 或者静态代码块赋值
    /**
     static{
        a = 0;
     }
     */
    final int b;//再声明的时候就需要赋值 或者代码块中赋值
    {
        b = 0;
    }
    public static void main(String[] args) {
        final int localA; // 局部变量只声明没有初始化，不会报错，与final无关
        localA = 0;  // 但是在使用前一定要赋值
        System.out.println(localA);
    }
}
