package org.hzz.tree;

/**
 * 二叉树的最大深度
 * https://leetcode.cn/problems/maximum-depth-of-binary-tree/
 */
public class MaximumDepth_104 {
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return Math.max(maxDepth(root.left),
                maxDepth(root.right)) + 1;

    }
}
