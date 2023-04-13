package org.hzz.assertt;

public class AssertDemo {
    public static void main(String[] args) {
        int age = 20;
        assert age < 18: "age is not less than 18";
        System.out.println("age is greater than 18");
    }
}
/** 需要添加 -ea 参数
 * Exception in thread "main" java.lang.AssertionError: age is not less than 18
 * 	at org.hzz.assertt.AssertDemo.main(AssertDemo.java:6)
 */