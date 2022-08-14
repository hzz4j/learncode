package org.hzz.bit.base;

/**
 * 左移右移
 */
public class BitMove {
    public static void main(String[] args) {
        System.out.println("the -234567 is : " + Integer.toBinaryString(-234567));
        //有符号右移>>(若正数,高位补0,负数,高位补1)
        System.out.println("the 4>>1 is : " + Integer.toBinaryString(4>>1));
        System.out.println("the -234567>>1 is : " + Integer.toBinaryString(-234567>>1));

        System.out.println("");
        //有符号左移<<(若正数,高位补0,负数,高位补1)
        System.out.println("the -234567<<16 is : " + Integer.toBinaryString(-234567<<16));
        System.out.println("");
        //无符号右移>>>(不论正负,高位均补0)
        System.out.println("the 234567 is : " + Integer.toBinaryString(234567));
        System.out.println("the 234567>>>4 is : " + Integer.toBinaryString(234567>>>4));
        System.out.println("");
        //无符号右移>>>(不论正负,高位均补0)
        System.out.println("the -4 is : " + Integer.toBinaryString(-4));
        System.out.println("the -4>>>4 is : " + Integer.toBinaryString(-4>>>4));
        System.out.println("the -4>>4 is : " + Integer.toBinaryString(-4>>1));
    }
}
/**
 * the -234567 is : 11111111111111000110101110111001
 * the 4>>1 is : 10
 * the -234567>>1 is : 11111111111111100011010111011100
 *
 * the -234567<<16 is : 1101011101110010000000000000000
 *
 * the 234567 is : 111001010001000111
 * the 234567>>>4 is : 11100101000100
 *
 * the -4 is : 11111111111111111111111111111100
 * the -4>>>4 is : 1111111111111111111111111111
 * the -4>>4 is : 11111111111111111111111111111110
 */