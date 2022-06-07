package org.hzz.flyweight;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class TreeFactory {
    private static Map<String,Tree> cache = new ConcurrentHashMap<>();

    public static Tree getTreeInstance(String name,Object data){
        Function<String, Tree> createTree = key -> new Tree(key,data);
        return cache.computeIfAbsent(name, createTree);
    }

    public static int getSize(){
        return cache.size();
    }
}
