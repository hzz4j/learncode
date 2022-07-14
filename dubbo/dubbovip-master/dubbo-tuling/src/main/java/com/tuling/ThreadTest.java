package com.tuling;

import org.apache.dubbo.common.utils.Holder;

public class ThreadTest {

    public Holder<Object> holder = new Holder<>();

    private void add() {
        synchronized (holder) {
            holder.set(new Object());
        }
    }


    public static void main(String[] args) {
        ThreadTest threadTest = new ThreadTest();
        threadTest.add();
    }
}
