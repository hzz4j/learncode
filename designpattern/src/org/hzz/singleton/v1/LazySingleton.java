package org.hzz.singleton.v1;

public class LazySingleton {
    private volatile static LazySingleton instance;

    private LazySingleton(){}

    public static LazySingleton getInstance(){
        if(instance == null){
            synchronized (LazySingleton.class){
                if(instance == null){
                    instance = new LazySingleton();
                    // 字节码层
                    // JIT ， CPU 有可能对如下指令进行重排序
                    // 1 .分配空间
                    // 2 .初始化
                    // 3 .引用赋值
                    // 如重排序后的结果为如
                    // 1 .分配空间
                    // 3 .引用赋值 如果在当前指令执行完，有其他线程来获取实例，将拿到尚未初始化好的实例
                    // 2 .初始化
                }
            }
        }
        return instance;
    }
}
