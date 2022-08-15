package org.hzz.linkedlist;

public class ReverseLinkedListI_206 {
    public ListNode reverseList(ListNode head) {
        ListNode pre = null,current = head, next = null;
        while(current != null){
            next = current.next;
            current.next = pre;
            pre = current;
            current = next;
        }
        return pre;
    }
}
