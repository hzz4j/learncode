package org.hzz;

import java.util.PriorityQueue;

public class PriorityQueueTest2 {
    public static void main(String args[])
    {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b)->b-a);
        for(int i=0;i<3;i++){
            pq.add(i);
            pq.add(1);
        }
        while(!pq.isEmpty()){
            System.out.print(pq.poll()+" ");
        }
        System.out.println("");
    }
}
/**
 * 2 1 1 1 1 0
 */