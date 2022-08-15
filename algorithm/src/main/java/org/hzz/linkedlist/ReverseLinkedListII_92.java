package org.hzz.linkedlist;

import java.util.List;

public class ReverseLinkedListII_92 {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode temp = new ListNode(0);
        temp.next = head;
        ListNode pre = temp;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }
        ListNode cur = pre.next;
        ListNode next = cur.next;

        for (int i = 0; i < right - left; i++) {
           cur.next = next.next;
           next.next = pre.next;
           pre.next = next;
           next = cur.next;
        }
        return temp.next;
    }
}
