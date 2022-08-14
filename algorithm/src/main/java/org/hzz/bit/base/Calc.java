package org.hzz.bit.base;

public class Calc {
    public static void main(String[] args) {
        /*取模a % (2的幂) 等价于 a & (2的幂 - 1)*/
        System.out.println("the 345 % 32 is : " + (345%32)+" or "+(345&(32-1)));

        /*判断奇偶数*/
        System.out.println(4 % 2 == 0 ? "偶数":"奇数");
        System.out.println(5 % 2 == 0 ? "偶数":"奇数");

        System.out.println((4 & 1) == 0 ? "偶数":"奇数");
        System.out.println((5 & 1) == 0 ? "偶数":"奇数");

        /*实现数字翻倍或减半*/
        System.out.println(9>>1);
        System.out.println(9<<1);

        /*交换两数*/
        int a = 4 ,b = 6;
        int temp = a;
        a = b;
        b = temp;
        System.out.println("a="+a+",b="+b);

        /*不借助临时变量交换两数*/
        a = a + b;
        b = a - b;
        a = a - b;
        System.out.println("a="+a+",b="+b);

        /*位操作交换两数*/
        a=a^b;
        b=a^b;// b=(a^b)^b=a^(b^b)=a
        a=a^b;// a=(a^b)^a=(a^a)^b=b
        System.out.println("a="+a+",b="+b);
    }
}
/**
 * the 345 % 32 is : 25 or 25
 * 偶数
 * 奇数
 * 偶数
 * 奇数
 * 4
 * 18
 * a=6,b=4
 * a=4,b=6
 * a=6,b=4
 */