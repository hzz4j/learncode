package org.hzz.array;



public class Medianof2SortedArrays_4 {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length,n = nums2.length;
        /*将奇数个数和偶数个数情况统一处理
         * 奇数个数: left==right
         * 偶数个数: left==right+1 */
        int left = (m+n+1)/2;
        int right = (m+n+2)/2;
        return (getKth(nums1,0,nums2,0,left) +
                getKth(nums1,0,nums2,0,right)) /2.0;
    }

    private int getKth(int[] nums1,int nums1Start,int[] nums2,int nums2Start,int k){
        // 两个数组中如果有一个数组空了，或者说k/2的下标值超过了数组的长度，就可以直接返回结果了
        if(nums1Start > nums1.length - 1) return nums2[nums2Start + k - 1];
        if(nums2Start > nums2.length - 1) return nums1[nums1Start + k - 1];
        // find it
        if(k == 1) return Math.min(nums1[nums1Start],nums2[nums2Start]);

        int minMid1 = Integer.MAX_VALUE,minMid2 = Integer.MAX_VALUE;
        if(nums1Start+k/2 - 1 < nums1.length){
            minMid1 = nums1[nums1Start+k/2 - 1];
        }

        if(nums2Start+k/2 - 1 < nums2.length){
            minMid2 = nums2[nums2Start+k/2 - 1];
        }

        if(minMid1 < minMid2){
            return getKth(nums1,nums1Start + k/2,nums2,nums2Start,k-k/2);
        }else{
            return getKth(nums1,nums1Start,nums2,nums2Start+k/2,k-k/2);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Medianof2SortedArrays_4().findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));
        System.out.println(new Medianof2SortedArrays_4().findMedianSortedArrays(new int[]{1}, new int[]{2,3,4,5,6}));
    }
}
/**
 * 2.0
 * 3.5
 */
