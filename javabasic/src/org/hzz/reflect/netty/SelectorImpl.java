package org.hzz.reflect.netty;

import java.util.HashSet;
import java.util.Set;

public abstract class SelectorImpl {
//    protected Set<Integer> selectedKeys = new HashSet();
    protected final Set<Integer> selectedKeys = new HashSet();
    static {
        System.out.println("run?");
    }
    public abstract int select();
}
