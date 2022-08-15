package org.hzz.array;

import java.util.Arrays;

public class KthInArray_215_HeapSort {
    private static int len;
    public int findKthLargest(int[] nums, int k) {
        len = nums.length;
        buildMaxHeap(nums);
        System.out.println(Arrays.toString(nums));
        for (int i = 0; i<k;i++){
            swap(nums,0,len - 1);
            len--;
            adjustHeap(nums,0);
            System.out.println(Arrays.toString(nums));
        }
        return nums[nums.length - k];
    }

    private void buildMaxHeap(int[] arrays){
        for (int i= len/2-1;i>=0;i--){
            adjustHeap(arrays,i);
        }
    }

    private void adjustHeap(int[] arrays,int index){
        int maxIndex = index;
        int left = index*2 + 1;
        int right = 2*(index+1);

        if(left < len && arrays[left] > arrays[maxIndex]){
            maxIndex = left;
        }

        if(right<len && arrays[right] > arrays[maxIndex]){
            maxIndex = right;
        }

        if (maxIndex != index){
            swap(arrays,maxIndex,index);
            adjustHeap(arrays,maxIndex);
        }
    }

    private void swap(int[] arrays,int i,int j){
        int temp = arrays[i];
        arrays[i] = arrays[j];
        arrays[j] = temp;
    }

    public static void main(String[] args) {
        System.out.println(new KthInArray_215_HeapSort().findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));
    }
}
/**
 * [6, 5, 4, 3, 2, 1]
 * [5, 3, 4, 1, 2, 6]
 * [4, 3, 2, 1, 5, 6]
 * 5
 */