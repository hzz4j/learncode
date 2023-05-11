package org.hzz.autobox;

public class IntegerTest {
    public static void main(String[] args) {
        Integer a = 100,b = 100;
        Integer c = 200,d = 200;
        System.out.println("a == b " + (a == b));
        System.out.println("c == d " + (c == d));
    }
}
/**
 * a == b true
 * c == d false
 */