package org.hzz.stack;

import java.util.Deque;
import java.util.LinkedList;

public class LargestRectangle_84V2 {
    public int largestRectangleArea(int[] heights) {
        int maxArea = Integer.MIN_VALUE;
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[i] < heights[stack.peek()]){
                Integer idx = stack.pop();
                int height = heights[idx];

                // find width
                int width = 0;
                if(stack.isEmpty()){
                    width = i;
                }else{
                    width = i - stack.peek() - 1;
                }
                maxArea = Math.max(maxArea,height * width);
            }
            stack.push(i);
        }

        while(!stack.isEmpty()){
            int idx = stack.pop();
            int height = heights[idx];
            // find width
            int width = 0;
            if(stack.isEmpty()){
                width = heights.length;
            }else{
                width = heights.length - stack.peek() - 1;
            }
            maxArea = Math.max(maxArea,height * width);
        }
        return maxArea;
    }


    public static void main(String[] args) {
        System.out.println(new LargestRectangle_84V2().largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
        System.out.println(new LargestRectangle_84V2().largestRectangleArea(new int[]{2, 4}));
    }
}
/**
 * 10
 * 4
 */