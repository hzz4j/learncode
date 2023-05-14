package org.hzz.random;

import java.util.Arrays;
import java.util.Random;

public class PseudoRandomNumberDemo {
    public static void main(String[] args) {
        int[] arr1 = new int[10];
        int[] arr2 = new int[10];
        // 种子都一样
        long seed = 100l;
        Random random1 = new Random(seed);
        Random random2 = new Random(seed);
        for (int i = 0;i<10;i++){
            arr1[i] = random1.nextInt(10);
            arr2[i] = random2.nextInt(10);
        }

        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr2));
    }
}
/**
 * [5, 0, 4, 8, 1, 6, 6, 8, 3, 3]
 * [5, 0, 4, 8, 1, 6, 6, 8, 3, 3]
 */
