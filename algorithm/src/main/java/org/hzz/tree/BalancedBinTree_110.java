package org.hzz.tree;

/**
 * 平衡二叉树
 * https://leetcode.cn/problems/balanced-binary-tree/
 */
public class BalancedBinTree_110 {

    public boolean isBalanced(TreeNode root) {
        if(root == null){
            return true;
        }
        return depth(root) != -1;
    }
    private int depth(TreeNode root){
        if(root == null) return 0;

        int left = depth(root.left);
        int right = depth(root.right);

        if(left == -1 || right == -1 || Math.abs(left-right)>1){
            return -1;
        }
        return Math.max(left,right)+1;
    }
}
