package org.hzz.observer;

import org.hzz.observer.ObServer;

public interface Subject {
    void attach(ObServer obServer);
    void remove(ObServer obServer);
    void notifyAllObServer(Object obj);
}
