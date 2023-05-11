package org.hzz.interviews.inner;

public class Test {
    public static void main(String[] args) {
        Test test = new Test();
        test.test(3);
    }

    // 局部变量a,b
    public void test(final int b){ // jdk8在这里做了优化，不用写,语法糖，但实际上也是有的，也不能修改
        final int a = 1;
        // 匿名内部类
        new Thread(){
            public void run(){
                System.out.println("a="+a);
                System.out.println("b="+b);
            }
        }.start();
    }
}


