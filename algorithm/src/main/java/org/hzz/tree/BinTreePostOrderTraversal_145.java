package org.hzz.tree;

import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树的后序遍历
 * https://leetcode.cn/problems/binary-tree-postorder-traversal/
 */
public class BinTreePostOrderTraversal_145 {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        traverTree(root, res);
        return res;
    }

    private void traverTree(TreeNode root, List<Integer> res) {
        if(root == null) return;
        traverTree(root.left,res);
        traverTree(root.right,res);
        accessData(root,res);
    }

    private void accessData(TreeNode root,List<Integer> res){
        res.add(root.val);
    }

    public List<Integer> postorderTraversalWithLoop(TreeNode root){
        List<Integer> res = new ArrayList<Integer>();
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        TreeNode preAccessNode = null;
        while (root != null || !stack.isEmpty()){
            while (root != null){
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if(root.right == null || root.right == preAccessNode){
                res.add(root.val);
                preAccessNode = root;
                root = null; // 方便从栈中弹出元素
            }else{
                stack.push(root);
                root = root.right;
            }
        }
        return res;
    }
}
