package org.hzz.stack;

import java.util.Deque;
import java.util.LinkedList;

public class DailyTemperatures_739 {
    public int[] dailyTemperatures(int[] temperatures) {
        int[] res = new int[temperatures.length];
        Deque<Integer> stack = new LinkedList<>(); // 存储的是下标

        for (int i = 0; i < temperatures.length; i++) {
            // 注意是while不是if，因为维护单调栈
            while(!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]){
                // 开始记录
                Integer targetIndex = stack.pop();
                res[targetIndex] = i - targetIndex;
            }
            stack.push(i);
        }
        return res;
    }
}
