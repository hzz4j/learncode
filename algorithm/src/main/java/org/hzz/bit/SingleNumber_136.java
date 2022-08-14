package org.hzz.bit;

/**
 * 只出现一次的数字
 * https://leetcode.cn/problems/single-number/
 */
public class SingleNumber_136 {
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num :
                nums) {
            result ^= num;
        }
        return result;
    }
}
