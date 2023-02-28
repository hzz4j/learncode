package org.hzz.templatemethod;

public class MainTest {
    public static void main(String[] args) {
        AbstractClass ac = new SubClass1();
        ac.operation();
    }
}
/**
 *  pre ...
 *  step1
 *  step2
 * SubClass1 method execute...
 */