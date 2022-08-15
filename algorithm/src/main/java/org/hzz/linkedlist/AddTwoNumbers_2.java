package org.hzz.linkedlist;

public class AddTwoNumbers_2 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;
        ListNode header = new ListNode(),currentNode = header;
        while(l1 != null || l2 != null || carry == 1){
            if(l1 != null){
                carry += l1.val;
                l1 = l1.next;
            }
            if(l2 != null){
                carry += l2.val;
                l2 = l2.next;
            }
            currentNode.next =  new ListNode(carry % 10);
            carry = carry / 10;
            currentNode = currentNode.next;
        }
        return header.next;
    }
}
