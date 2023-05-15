package org.hzz.collection.set;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class HashSetDemo {
    public static void main(String[] args) {
        Set<String> set = new HashSet();
        set.add("dog");
        set.add("camel");
        set.add("cat");
        set.add("ant");
        System.out.println(set); // [camel, ant, cat, dog]
    }
}