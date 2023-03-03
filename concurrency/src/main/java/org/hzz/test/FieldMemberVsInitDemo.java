package org.hzz.test;

public class FieldMemberVsInitDemo {
    private A a = new A();
    public FieldMemberVsInitDemo(){
        System.out.println("FieldMemberVsInitDemo init...");
    }

    class A{
        public A(){
            System.out.println("A init...");
        }
    }
    public static void main(String[] args) {
        new FieldMemberVsInitDemo();
    }
}
/**
 * A init...
 * FieldMemberVsInitDemo init...
 */