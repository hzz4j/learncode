package org.hzz.stack;

public class LargestRectangle_84V1 {
    public int largestRectangleArea(int[] heights) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < heights.length; i++) {
            max = Math.max(calcElementArea(heights,i),max);
        }
        return max;
    }

    private int calcElementArea(int[] heights,int pos){
        // find left
        int count = 1;
        for (int i = pos-1; i >= 0; i--){
            if(heights[i] < heights[pos]) break;
            count++;
        }
        // find right
        for (int i = pos+1; i < heights.length; i++){
            if(heights[i] < heights[pos]) break;
            count++;
        }
        int area = count * heights[pos];
        System.out.println(heights[pos]+ " --> "+area);
        return area;
    }
    public static void main(String[] args) {
        System.out.println(new LargestRectangle_84V1().largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
    }
}
/**
 * 2 --> 2
 * 1 --> 6
 * 5 --> 10
 * 6 --> 6
 * 2 --> 8
 * 3 --> 3
 * 10
 */