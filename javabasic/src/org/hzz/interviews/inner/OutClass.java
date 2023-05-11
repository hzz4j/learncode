package org.hzz.interviews.inner;

public class OutClass {
    private int age = 18;

    public static void main(String[] args) {
        OutClass outClass = new OutClass();
        outClass.outprint(3);
    }
    public void outprint(final int a){
        final int b = 5;
        class InnerClass{
            public void innerPrint(){
                System.out.println("a="+a);
                System.out.println("b="+b);
                System.out.println("age="+age);
            }
        }

        new InnerClass().innerPrint();
    }
}
