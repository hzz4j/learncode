package org.hzz.interviews;

public class StringEqualsDemo {
    public static void main(String[] args) {
        String str1 = "Hello";
        String str2 = new String("Hello");
        String str3 = str2; // 引用传递
        System.out.println(str1 == str2); // false
        System.out.println(str1 == str3); // false
        System.out.println(str2 == str3); // true
        System.out.println(str1.equals(str2)); // true
        System.out.println(str1.equals(str3)); // true
        System.out.println(str2.equals(str3)); // true
    }
}
