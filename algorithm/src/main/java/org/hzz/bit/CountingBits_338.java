package org.hzz.bit;

public class CountingBits_338 {
    public int[] countBits1(int n) {
        int bits[] = new int[n+1];
        for (int i = 1; i <= n; i++) {
            bits[i] = bits[i&(i-1)]+1;
        }
        return bits;
    }

    public int[] countBits(int n) {
        int bits[] = new int[n+1];
        for (int i = 1; i <= n; i++) {
            bits[i] = (i&1) == 1 ? bits[i-1]+1:bits[i>>1];
        }
        return bits;
    }
}
