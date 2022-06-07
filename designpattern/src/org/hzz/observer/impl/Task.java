package org.hzz.observer.impl;

import org.hzz.observer.ObServer;

public class Task implements ObServer {
    private String name = "Task1";

    public Task(String name){
        this.name = name;
    }
    @Override
    public void update(Object event) {
        System.out.format("%s receive %s\n",name,event);
    }
}
