package org.hzz.collection.map;

import java.util.*;

public class TreeMapDemo {
    public static void main(String[] args) {
        Map<String, Integer> map = new TreeMap<>();
        map.put("orange", 1);
        map.put("apple", 2);
        map.put("pear", 3);
        System.out.println(map); // {apple=2, orange=1, pear=3}
        orderValueDesc(map);
    }

    private static void orderValueDesc(Map<String,Integer> map){
        List<Map.Entry<String, Integer>> list = new ArrayList(map.entrySet());

        // 定义一个比较器Comparator
        Comparator<Map.Entry<String, Integer>> descValue = (item1,item2)
                -> item2.getValue().compareTo(item1.getValue());

        list.sort(descValue);
        System.out.println(list); // [pear=3, apple=2, orange=1]
    }
}
