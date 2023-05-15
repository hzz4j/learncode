package org.hzz.collection.vector;

import java.util.Vector;

public class VectorDemo {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("cat");
        vector.add("cat");
        vector.add("dog");
        vector.remove("cat"); // 会删除第一个遇到的元素
        System.out.println(vector); // [cat, dog]


    }
}
