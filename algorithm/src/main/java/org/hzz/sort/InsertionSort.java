package org.hzz.sort;

/**
 * 插入排序
 */
public class InsertionSort {
    public int[] sortArray(int[] nums){

        for (int i = 1; i < nums.length; i++) {
            int preIndex = i-1; // 已排序的数据索引
            int currentValue = nums[i];
            /*在已被排序过数据中倒序寻找合适的位置，如果当前待排序数据比比较的元素要小，
            将比较的元素元素后移一位*/
            while (preIndex>=0 && currentValue<nums[preIndex]){
                //将当前元素后移一位
                nums[preIndex+1] = nums[preIndex];
                preIndex--;
                PrintArray.print(nums);
            }
            /*while循环结束时，说明已经找到了当前待排序数据的合适位置，插入*/
            nums[preIndex+1] = currentValue;
            System.out.println("本轮被插入排序后的数组");
            PrintArray.print(nums);
            System.out.println("--------------------");
        }
        return nums;
    }

    public static void main(String[] args) {
        PrintArray.print(PrintArray.SRC);
        System.out.println("============================================");
        int[] dest = new InsertionSort().sortArray(PrintArray.SRC);
        PrintArray.print(dest);
    }
}
