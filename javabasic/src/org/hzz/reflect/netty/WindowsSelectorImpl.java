package org.hzz.reflect.netty;

public class WindowsSelectorImpl extends SelectorImpl{
    @Override
    public int select(){
        selectedKeys.add(1<<0);
        selectedKeys.add(1<<2);
        selectedKeys.add(1<<3);
        selectedKeys.add(1<<4);
        return this.selectedKeys.size();
    }
}
