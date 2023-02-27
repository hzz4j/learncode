package org.hzz.singleton.v3;

public class InnerClassSingleton {
    private static class InnerClassHolder{
        static {
            System.out.println("InnerClassHolder 初始化");
        }
        private static InnerClassSingleton instance = new InnerClassSingleton();
    }

    private InnerClassSingleton(){
        if(InnerClassHolder.instance == null){
            throw new RuntimeException("单例不允许多个实例");
        }
    }

    public static InnerClassSingleton getInstance(){
        return InnerClassHolder.instance;
    }
}
