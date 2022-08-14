package org.hzz.bit;

public class HammingDistance_461 {
    public int hammingDistance(int x, int y) {
        int z = x^y;
        int count = 0;
        while(z != 0){
            z = z&(z-1);
            count++;
        }
        return count;
    }
}
