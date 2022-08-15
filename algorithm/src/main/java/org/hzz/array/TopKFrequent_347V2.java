package org.hzz.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKFrequent_347V2 {

    public int[] topKFrequent(int[] nums, int k) {
        // count frequent
        Map<Integer,Integer> map = new HashMap<>();
        for (int num:
             nums) {
            map.put(num,map.getOrDefault(num,0)+1);
        }
        // [0] value,[1] frequence
        PriorityQueue<Integer[]> pq = new PriorityQueue<>(k,(a,b)->a[1] - b[1]);
        for (Map.Entry<Integer,Integer> entry:
             map.entrySet()) {
            if(pq.size() == k){
                if (pq.peek()[1] < entry.getValue()){
                    pq.poll();
                    pq.offer(new Integer[]{entry.getKey(),entry.getValue()});
                }
            }else{
                pq.offer(new Integer[]{entry.getKey(),entry.getValue()});
            }
        }
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = pq.poll()[0];
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(
                new TopKFrequent_347V2().topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2)
        ));
    }
}
/**
 * [2, 1]
 */