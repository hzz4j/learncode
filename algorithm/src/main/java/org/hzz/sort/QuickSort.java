package org.hzz.sort;

public class QuickSort {

    public int[] sortArray(int[] nums){
        return sort(nums,0,nums.length-1);
    }

    private int[] sort(int[] nums,int start,int end){
        if (nums.length < 1 || start < 0 || end >= nums.length || start > end)
            return null;

        /*数据分割成独立的两部分时，从哪儿分区的指示器*/
        int zoneIndex = partition(nums, start, end);
        if(zoneIndex > start){
            sort(nums,start,zoneIndex-1);
        }
        if(zoneIndex < end){
            sort(nums,zoneIndex+1,end);
        }
        System.out.println("本轮排序后的数组");
        PrintArray.printIndex(nums,start,end);
        System.out.println("--------------------");
        return nums;
    }

    private int partition(int[] nums,int start,int end){
        /*只有一个元素时，无需再分区*/
        if(start == end) return start;
        /*zoneIndex是分区指示器，初始值为分区头元素下标减一*/
        int zoneIndex = start - 1;
        /*随机选取一个基准数*/
        int pivot = (int)Math.floor(Math.random() * (end-start+1)) + start;
        System.out.println("开始下标："+start+",结束下标:"+end+",基准数下标："
                +pivot+",元素值:"+nums[pivot]+"，分区指示器下标："+zoneIndex);
        swap(nums,pivot,end); /*将基准数和分区尾元素交换位置*/
        for (int i = start; i <=end ; i++) {
            if(nums[i]<=nums[end]){
                zoneIndex++;  /*首先分区指示器累加*/
                if(i>zoneIndex){  /*当前元素在分区指示器的右边时，交换当前元素和分区指示器元素*/
                    swap(nums,zoneIndex,i);
                }
            }
            System.out.println("分区指示器："+zoneIndex+",遍历指示器:"+i);
            PrintArray.printIndex(nums,start,end);
        }
        System.out.println("---------------");
        return zoneIndex;
    }

    private void swap(int[] array,int i,int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        PrintArray.print(PrintArray.SRC);
        System.out.println("============================================");
        int[] dest = new QuickSort().sortArray(PrintArray.SRC);
        PrintArray.print(dest);
    }
}
