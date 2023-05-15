package org.hzz.collection.set;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetDemo {
    public static void main(String[] args) {
        Set<String> set = new TreeSet();
        set.add("dog");
        set.add("camel");
        set.add("cat");
        set.add("ant");
        System.out.println(set); // [ant, camel, cat, dog]
    }
}
