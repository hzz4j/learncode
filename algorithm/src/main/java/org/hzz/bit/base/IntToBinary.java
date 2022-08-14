package org.hzz.bit.base;

/**
 * 按位运算
 */
public class IntToBinary {

    public static void main(String[] args) {
        System.out.println("the 4 is : " + Integer.toBinaryString(4));
        System.out.println("the 6 is : " + Integer.toBinaryString(6));

        //位与&(1&1=1 0&0=0 1&0=0)
        System.out.println("the 4&6 is : " + Integer.toBinaryString(6&4));

        //位或|(1|1=1 0|0=0 1|0=1)
        System.out.println("the 4|6 is : " + Integer.toBinaryString(6|4));

        //位非~(~1=0  ~0=1)
        System.out.println("the ~4 is : " + Integer.toBinaryString(~4));

        //位异或^(1^1=0 1^0=1  0^0=0)
        System.out.println("the 4^6 is : " + Integer.toBinaryString(6^4));
        System.out.println("the 17^17 is : " + Integer.toBinaryString(17^17));
    }
}
/**
 * the 6 is : 110
 * the 4&6 is : 100
 * the 4|6 is : 110
 * the ~4 is : 11111111111111111111111111111011
 * the 4^6 is : 10
 * the 17^17 is : 0
 */