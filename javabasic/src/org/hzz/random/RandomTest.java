package org.hzz.random;

import java.util.Arrays;
import java.util.Random;

public class RandomTest {
    public static void main(String[] args) {
        int[] arr1 = new int[10];
        int[] arr2 = new int[10];
        long seed = 100l;
        for (int i = 0;i<10;i++){
            arr1[i] = new Random().nextInt(10);
            arr2[i] = new Random(seed).nextInt(10);
        }

        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr2));
    }
}
/**
 * [0, 1, 1, 9, 6, 6, 4, 2, 9, 0]
 * [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]
 */
