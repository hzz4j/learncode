package org.hzz.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 对称二叉树
 * https://leetcode.cn/problems/symmetric-tree/
 */
public class SymmetricTree_101 {
    public boolean isSymmetric(TreeNode root) {
        if(root==null) {
            return true;
        }
        //调用递归函数，比较左节点，右节点
        return deepCheck(root.left,root.right);
    }

    private boolean deepCheck(TreeNode left, TreeNode right) {
        //递归的终止条件是两个节点都为空
        //或者两个节点中有一个为空
        //或者两个节点的值不相等
        if(left == null && right == null){
            return true;
        }

        if(left == null || right == null){
            return false;
        }

        if(left.val != right.val){
            return false;
        }
        //再递归的比较 左节点的左孩子 和 右节点的右孩子
        //以及比较  左节点的右孩子 和 右节点的左孩子
        return deepCheck(left.left,right.right) && deepCheck(left.right,right.left);
    }


    public boolean isSymmetricWithQueue(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode u,v;
        if(root == null || (root.left == null && root.right==null)) return true;

        queue.offer(root.left);
        queue.offer(root.right);

        while (!queue.isEmpty()){
            u = queue.poll();
            v = queue.poll();

            if(u == null && v == null){
                continue;
            }

            if((u == null || v == null) ||
                    (u.val != v.val)){
                return false;
            }
            queue.offer(u.left);
            queue.offer(v.right);

            queue.offer(u.right);
            queue.offer(v.left);
        }
        return true;
    }
}
