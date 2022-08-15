package org.hzz.sort;

/**
 * 冒泡排序
 */
public class BubbleSort {
    public int[] sortArray(int[] nums){
        if (nums.length == 0) return nums;
        for (int i = 0;i < nums.length - 1;i++){ // 控制轮询次数
            /*从第0个元素开始，依次和后面的元素进行比较
             * j < array.length - 1 - i表示第[array.length - 1 - i]
             * 个元素已经冒泡到了合适的位置，无需进行比较，可以减少比较次数*/
            for (int j = 0; j < nums.length - 1 - i; j++) { // j是指示器
                if(nums[j] > nums[j+1]){
                    int temp = nums[j];
                    nums[j] = nums[j+1];
                    nums[j+1] = temp;
                }
                PrintArray.print(nums);
            }
            System.out.println("---------------");
        }
        return nums;
    }

    public static void main(String[] args) {
        PrintArray.print(PrintArray.SRC);
        System.out.println("============================================");
        int[] dest = new BubbleSort().sortArray(PrintArray.SRC);
        PrintArray.print(dest);
    }
}
