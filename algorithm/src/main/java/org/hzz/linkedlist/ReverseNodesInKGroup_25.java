package org.hzz.linkedlist;

public class ReverseNodesInKGroup_25 {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode temp = new ListNode(),p = temp,begin = temp;
        temp.next = head;
        int i = 0;
        while(p != null){
            i++;
            p = p.next;
            if(i%k == 0 && p != null){  // 不够了则不反转
                begin = reverse(begin,k);
                p = begin;
                //PrintListNode.print(temp.next);
            }
        }
        return temp.next;
    }

    private ListNode reverse(ListNode begin,int k){
        ListNode prev = begin,cur = prev.next,next = cur.next;
        for (int i = 0; i < k-1; i++) { // k-1，比如要反转两个元素，只需要遍历一次即可
            cur.next = next.next;
            next.next = prev.next;
            prev.next = next;
            next = cur.next;
        }
        return cur;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1),p = head;
        for(int i=2;i<=5;i++){
            p.next = new ListNode(i);
            p = p.next;
        }
        PrintListNode.print(head);
        head = new ReverseNodesInKGroup_25().reverseKGroup(head, 2);
        System.out.println("最终结果：");
        PrintListNode.print(head);
    }
}
/**
 * [1,2,3,4,5]
 * [2,1,3,4,5]
 * [2,1,4,3,5]
 * 最终结果：
 * [2,1,4,3,5]
 */