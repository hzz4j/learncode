package org.hzz.autobox;

public class IntegerSimpleTest2 {
    public static void main(String[] args) {
        Integer a = 200;
        int b = 200;
        Integer c = 200;
        // a会进行拆箱操作，所以a == b为true
        System.out.println("a == b " + (a == b));
        // a和c经过自动装箱，都超过了Integer.cache,都是new出来的新Integer对象，所以a == c为false
        System.out.println("a == c " + (a == c));
    }
}
/**
 * a == b true
 * a == c false
 */
