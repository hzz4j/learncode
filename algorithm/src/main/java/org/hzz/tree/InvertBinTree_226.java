package org.hzz.tree;

/**
 * 翻转二叉树
 * https://leetcode.cn/problems/invert-binary-tree/
 */
public class InvertBinTree_226 {
    public TreeNode invertTree(TreeNode root) {
        return inverseTree(root);
    }

    private TreeNode inverseTree(TreeNode root){
        if(root == null) return root;
        TreeNode left = inverseTree(root.left);
        TreeNode right = inverseTree(root.right);

        root.left = right;
        root.right = left;
        return root;
    }
}
