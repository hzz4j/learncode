package org.hzz;

import java.util.*;

/**
 * 94. 二叉树的中序遍历
 * https://leetcode.cn/problems/binary-tree-inorder-traversal/
 */
public class BinTreeInOrderTraversal_94 {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        traverTree(root,res);
        return res;
    }

    private void traverTree(TreeNode root,List<Integer> res){
        if(root == null) return;
        traverTree(root.left,res);
        accessData(root,res);
        traverTree(root.right,res);
    }

    private void accessData(TreeNode root,List<Integer> res){
        res.add(root.val);
    }


    public List<Integer> inorderTraversalWithLoop(TreeNode root){
        List<Integer> res = new ArrayList<>();
        // 用双端队列实现stack
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        while(root != null || !stack.isEmpty()){
            while(root != null){
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            res.add(root.val);
            root = root.right;
        }
        return res;
    }
}
