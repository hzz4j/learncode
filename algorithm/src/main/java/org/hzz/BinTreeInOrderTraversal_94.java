package org.hzz;

import java.util.ArrayList;
import java.util.List;

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
}
