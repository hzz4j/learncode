package org.hzz.collection.set;


import java.util.LinkedHashSet;
import java.util.Set;

public class LinkedHashSetDemo {
    public static void main(String[] args) {
        Set<String> set = new LinkedHashSet();
        set.add("dog");
        set.add("camel");
        set.add("cat");
        set.add("ant");
        System.out.println(set); // [dog, camel, cat, ant]

        String[] strings = set.toArray(new String[set.size()]);
    }
}
