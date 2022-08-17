package org.hzz.soft;

import org.hzz.User;

import java.lang.ref.SoftReference;

public class SoftMain {
    public static void main(String[] args) throws InterruptedException {
        SoftReference<User> userSoftReference = new SoftReference<User>(new User());
        System.out.println(userSoftReference.get()); // org.hzz.User@1b6d3586
        System.gc();
        Thread.sleep(2000); // 保证gc
        System.out.println(userSoftReference.get()); // org.hzz.User@1b6d3586
    }
}
