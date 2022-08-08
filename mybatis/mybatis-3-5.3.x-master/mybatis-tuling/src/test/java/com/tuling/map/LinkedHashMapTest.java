package com.tuling.map;

import java.util.LinkedHashMap;
import java.util.Map;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class LinkedHashMapTest {

    public static void main(String[] args) {
        final int SIZE=10;
        Map map = new LinkedHashMap(SIZE,.75F,true){
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
               return this.size()>SIZE;
            }
        };

        System.out.println(map.size());
        for(int i = 0; i < 20; i++) {
            map.put(i, true);
            // 第0个永远不会被移除，因为我一直在get
            System.out.println(map.getOrDefault(0,false));
            System.out.println(map.size());
            System.out.println(map);
        }
    }
}
