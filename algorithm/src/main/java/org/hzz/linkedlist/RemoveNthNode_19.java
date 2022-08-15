package org.hzz.linkedlist;

public class RemoveNthNode_19 {
    // 注意[1] 1的情况
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode startNode = new ListNode(0);
        ListNode slow =  startNode, fast = startNode;
        startNode.next = head;
        // 先移动fast
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }
        while (fast!=null){
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        // 不能返回return head 因为[1] 1的情况，head还是指向这1
        return startNode.next;
    }
}
