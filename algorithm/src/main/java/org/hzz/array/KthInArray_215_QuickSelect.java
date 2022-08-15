package org.hzz.array;

public class KthInArray_215_QuickSelect {

    public int findKthLargest(int[] nums, int k) {
        int target = nums.length - k;
        int start = 0,end = nums.length - 1;
        while(true){
            int zoneIndex = partion(nums,start,end);
            if(zoneIndex == target){
                return nums[target];
            }else if(zoneIndex < target){
                start = zoneIndex + 1;
            } else{
                end = zoneIndex - 1;
            }
        }
    }

    private int partion(int[] nums,int start,int end){
        if(start == end) return start;
        // 基准数的选择，会提升优化的效率
        int pivot = (int) (start + Math.random() * (end - start + 1));
        int zoneIndex = start - 1;
        swap(nums,pivot,end);
        for (int i = start; i <= end; i++) {
            // 注意等于
            if(nums[i] <= nums[end]){
                zoneIndex++;
                if(i > zoneIndex){
                    swap(nums,i,zoneIndex);
                }
            }
        }
        return zoneIndex;
    }

    private void swap(int[] arrays,int i,int j){
        int temp = arrays[i];
        arrays[i] = arrays[j];
        arrays[j] = temp;
    }

}
