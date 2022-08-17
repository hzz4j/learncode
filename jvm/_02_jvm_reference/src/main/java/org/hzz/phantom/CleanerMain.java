package org.hzz.phantom;

import sun.misc.Cleaner;

class User{
    private Cleaner cleaner;
    public User(Runnable callback){
        Cleaner.create(this,callback);
    }
}
public class CleanerMain {
    public static void main(String[] args) throws InterruptedException {
        User user = new User(()->{
            System.out.println("回调执行了");
        });

        user = null;
        System.gc();
        Thread.sleep(2000);
    }
}
/**
 * 回调执行了
 */