package org.hzz.linkedlist;

import java.util.List;

public class PrintListNode {
    public static void print(ListNode node){
        StringBuilder s = new StringBuilder();
        s.append("[");
        boolean first = true;
        while(node != null){
            if(first) {
                first = false;
            }else{
                s.append(",");
            }
            s.append(node.val);
            node = node.next;
        }
        s.append("]");
        System.out.println(s.toString());
    }
}
