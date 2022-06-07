package org.hzz.observer.impl;

import org.hzz.observer.ObServer;
import org.hzz.observer.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectImp implements Subject {
    private List<ObServer> container = new ArrayList<>();

    @Override
    public void attach(ObServer obServer) {
        container.add(obServer);
    }

    @Override
    public void remove(ObServer obServer) {
        container.remove(obServer);
    }

    @Override
    public void notifyAllObServer(Object obj) {
        for (ObServer observer:
             container) {
            observer.update(obj);
        }
    }
}
