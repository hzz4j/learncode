package org.hzz;

public class Test1 extends Object{
    private A a;
    public Test1(){
        a = new A();
        System.out.println("===========A是由哪个类加载器加载的？=================");
        System.out.println(a.getClass().getClassLoader());
    }

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(Test1.class.getClassLoader());
    }

    public void methodB(){
        B b = new B();
        System.out.println("===========B是由哪个类加载器加载的？=================");
        System.out.println(b.getClass().getClassLoader());
    }

}
