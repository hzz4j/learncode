package org.hzz.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKFrequent_347 {
    static class Element{
        private int val;
        private int frequent;
        public Element(int val,int frequent){
            this.val = val;
            this.frequent = frequent;
        }

        @Override
        public String toString() {
            return "Element{" +
                    "val=" + val +
                    ", frequent=" + frequent +
                    '}';
        }
    }
    public int[] topKFrequentWithPQ(int[] nums, int k) {
        // count frequent
        Map<Integer,Integer> map = new HashMap<>();
        for (int num:
             nums) {
            map.put(num,map.getOrDefault(num,0)+1);
        }
        System.out.println(map);
        PriorityQueue<Element> pq = new PriorityQueue<>(k,(a,b)->a.frequent - b.frequent);
        for (Map.Entry<Integer,Integer> entry:
             map.entrySet()) {
            if(pq.size() == k){
                if (pq.peek().frequent < entry.getValue()){
                    pq.poll();
                    pq.offer(new Element(entry.getKey(),entry.getValue()));
                }
            }else{
                pq.offer(new Element(entry.getKey(),entry.getValue()));
            }
        }
        int[] res = new int[k];
        System.out.println(pq);
        for (int i = 0; i < k; i++) {
            res[i] = pq.poll().val;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(
                new TopKFrequent_347().topKFrequentWithPQ(new int[]{1, 1, 1, 2, 2, 3}, 2)
        ));
    }
}
/**
 * {1=3, 2=2, 3=1}
 * [Element{val=2, frequent=2}, Element{val=1, frequent=3}]
 * [2, 1]
 */