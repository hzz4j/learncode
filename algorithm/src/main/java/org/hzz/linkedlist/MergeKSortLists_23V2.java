package org.hzz.linkedlist;

import java.util.PriorityQueue;

public class MergeKSortLists_23V2 {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) return null;
        // 最小堆
        PriorityQueue<ListNode> queue = new PriorityQueue<ListNode>(lists.length,(f,s)->f.val - s.val);

        // 初始化
        for (ListNode node : lists) {
            if(node != null) queue.offer(node);
        }

        ListNode head = new ListNode(),cur = head;
        while (!queue.isEmpty()){
            cur.next = queue.poll();
            cur = cur.next;
            if(cur.next != null){
                queue.offer(cur.next);
            }
        }
        return head.next;
    }
}
