package org.hzz;

public class StandardResolveResult implements ResolveResult{
    private int[] nums;
    private int start;
    private int end;
    public StandardResolveResult(int[] nums,int start,int end){
        this.nums = nums;
        this.start = start;
        this.end = end;
    }


    @Override
    public int resolve() {
        int result = 0;
        for (int i = start;i<end;i++){
            result += nums[i];
        }
        return result;
    }
}
