package org.hzz.linkedlist;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheV2 {
    private int capacity;
    private Map<Integer,Integer> cache;

    public LRUCacheV2(int capacity) {
        this.capacity = capacity;
        cache = new LinkedHashMap<Integer,Integer>(1024,0.75f,true){
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                boolean res = size()>capacity;
                if(res) System.out.println("删除: "+eldest);
                return res;
            }
        };
    }

    public int get(int key) {
        Integer res = cache.get(key);
        return res == null ? -1:res;
    }

    public void put(int key, int value) {
        cache.put(key,value);
    }

    public static void main(String[] args) {
        LRUCacheV2 lruCache = new LRUCacheV2(2);
        lruCache.put(1,1);
        lruCache.put(2,2);
        lruCache.put(3,3);
        System.out.println(lruCache.cache);  // 应该输出 {2=2,3=3}
        lruCache.get(2);
        lruCache.put(5,5);
        System.out.println(lruCache.cache); // 应该输出 {2=2,5=5}
    }
}
/**
 * 删除: 1=1
 * {2=2, 3=3}
 * 删除: 3=3
 * {2=2, 5=5}
 */