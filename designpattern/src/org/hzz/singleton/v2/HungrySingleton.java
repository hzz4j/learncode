package org.hzz.singleton.v2;

public class HungrySingleton {
    private final static HungrySingleton instance = new HungrySingleton();

    public HungrySingleton(){
        System.out.println("HungrySingleton 实例化");
    }

    public static HungrySingleton getInstance(){
        return instance;
    }
}
