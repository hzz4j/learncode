package org.hzz.interviews.list;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListDemo {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList();
        list.add(1); // 尾插法
        list.add(0,2); // 不是尾插法，会移动数据
        list.add(0,3); // 不是尾插法，会移动数据
        System.out.println(Arrays.toString(list.toArray()));
    }
}
/**
 * [3, 2, 1]
 */