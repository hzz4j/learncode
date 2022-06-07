package org.hzz.reflect.netty;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class NioEventLoop extends Thread{
    // 让这个set和WindowsSelectorImpl这个类的属性中的selectedKeys共享
    private Set<Integer> selectedKeys;
    private SelectorImpl selector;

    public NioEventLoop()  {
        selector = new WindowsSelectorImpl();
        doOpenSelector();
        start();
    }

    /**
     * 进行修改
     */
    private void doOpenSelector()  {
        try{
            HashSet<Integer> selectedKeys = new HashSet<>();
            Class<?> selectorImplClass = getSelectorImplClass();
            Field selectedKeysField = selectorImplClass.getDeclaredField("selectedKeys");
            trySetAccessible(selectedKeysField,true);
            selectedKeysField.set(selector,selectedKeys); // 设置共享通信
            this.selectedKeys = selectedKeys;       // 设置共享通信
        }catch (Exception xxx){}
    }

    public Class<?> getSelectorImplClass(){
        try{
            Class<?> selectorImplClass = Class.forName("org.hzz.reflect.netty.SelectorImpl",
                    false,ClassLoader.getSystemClassLoader());
            return selectorImplClass;
        }catch (Exception xxx){
            return null;
        }
    }

    public void trySetAccessible(AccessibleObject object, boolean checkAccessible){
        object.setAccessible(checkAccessible);
    }

    @Override
    public void run() {
        int select = selector.select();
        if(select != 0){
            this.selectedKeys.stream().forEach(System.out::println);
        }
    }
}
