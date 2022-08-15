package org.hzz.sort;

/**
 * 堆排序
 */
public class HeapSort {
    private int len;

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public int[] sortArray(int[] nums){
        len = nums.length;
        if(len < 2) return nums;
        buildMaxHeap(nums); /*1.构建一个最大堆*/
        /*2.循环将堆首位（最大值）与未排序数据末位交换，然后重新调整为最大堆*/
        while (len>0){
            swap(nums,0,len-1);
            len--;
            adjustHeap(nums,0);
            PrintArray.print(nums);
            System.out.println("--------------------");
        }
        return nums;
    }

    private void buildMaxHeap(int[] nums){
        /*从最后一个非叶子节点开始向上构造最大堆*/
        for (int i = (len/2-1); i >= 0 ; i--) {
            adjustHeap(nums,i);
        }
        System.out.println("构造完成最大堆");
        PrintArray.print(nums);
        System.out.println("============================================");
    }


    private void adjustHeap(int[] nums,int i){
        int maxIndex = i;
        int left = 2*i+1;  // 左子树
        int right = 2*(i+1); // 右子树
        /*如果有左子树，且左子树大于父节点，则将最大指针指向左子树*/
        if(left < len &&  nums[left] > nums[maxIndex]){
            maxIndex = left;
        }
        /*如果有右子树，且右子树大于父节点且大于左子树，则将最大指针指向右子树*/
        if(right < len && nums[right] > nums[maxIndex]){
            maxIndex = right;
        }
        /*如果父节点不是最大值，则将父节点与最大值交换，并且递归调整与父节点交换的位置。*/
        if(maxIndex != i){
            swap(nums,i,maxIndex);
            adjustHeap(nums,maxIndex);
        }
    }

    public static void main(String[] args) {
        PrintArray.print(PrintArray.SRC);
        System.out.println("============================================");
        int[] dest = new HeapSort().sortArray(PrintArray.SRC);
        PrintArray.print(dest);
    }
}
