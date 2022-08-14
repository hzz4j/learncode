package org.hzz.tree;

import java.util.LinkedList;
import java.util.Queue;

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


    public int maxDepthWithQueue(TreeNode root) {
        if(root == null) return 0;

        int deepth = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            // 每一层的元素个数
            int size = queue.size();
            while(size > 0){
                TreeNode node = queue.poll();
                if(node.left != null) queue.offer(node.left);
                if(node.right != null) queue.offer(node.right);
                size--;
            }
            deepth++;
        }
        return deepth;
    }
}
