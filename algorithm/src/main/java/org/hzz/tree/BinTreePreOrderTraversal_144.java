package org.hzz.tree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树的前序遍历
 * https://leetcode.cn/problems/binary-tree-preorder-traversal/
 */
public class BinTreePreOrderTraversal_144 {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        traverTree(root,res);
        return res;
    }

    private void traverTree(TreeNode root,List<Integer> res){
        if(root == null) return;
        accessData(root,res);
        traverTree(root.left,res);
        traverTree(root.right,res);
    }

    private void accessData(TreeNode root,List<Integer> res){
        res.add(root.val);
    }

    public List<Integer> preorderTraversalWithLoop(TreeNode root){
        List<Integer> res = new ArrayList<>();
        // 用双端队列实现stack
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        while(root != null || !stack.isEmpty()){
           while (root != null){
               accessData(root,res);
               stack.push(root);
               root = root.left;
           }
           root = stack.pop();
           root = root.right;
        }
        return res;
    }
}
