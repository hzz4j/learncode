package org.hzz.linkedlist;

public class ReorderList_143 {
    public void reorderList(ListNode head) {
        if(head == null || head.next == null) return;
        // 找中间节点 奇数节点时返回第一个
        ListNode slow = head,fast = head;
        while (fast.next != null && fast.next.next !=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        // 中间节点
        ListNode preMid = slow;
        // 反转
        ListNode preCur = preMid.next;
        while(preCur.next != null){  // 这一步骤很巧妙
            ListNode current = preCur.next; // 这一步也骤很巧妙
            preCur.next = current.next;
            current.next = preMid.next;
            preMid.next = current;
        }

        /*“梅花间隔”的形式将反转后的链表插入到前半部分链表*/
        slow = head;
        fast = preMid.next;
        while(slow != preMid){  // fast != null [1,2,3,4] 会出现死循环，因为当slow=preMid的时候，fast用于不可能为空
            preMid.next = fast.next;
            fast.next = slow.next;
            slow.next = fast;
            // move
            slow = fast.next;
            fast = preMid.next;
        }
    }
}
