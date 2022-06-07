package org.hzz.flyweight;

public class FlyweightTest {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(101,100,TreeFactory.getTreeInstance("xxx","something else"));
        TreeNode node2 = new TreeNode(102,102,TreeFactory.getTreeInstance("xxx","something else"));
        TreeNode node3 = new TreeNode(103,103,TreeFactory.getTreeInstance("xxx","something else"));
        TreeNode node4 = new TreeNode(104,105,TreeFactory.getTreeInstance("xxx","something else"));

        System.out.println(TreeFactory.getSize());
    }
}
