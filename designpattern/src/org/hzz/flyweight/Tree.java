package org.hzz.flyweight;

public class Tree {
    private final String name;
    private final Object data;

    public Tree(String name, Object data) {
        this.name = name;
        this.data = data;
        System.out.println("Create tree: "+name);
    }
}
