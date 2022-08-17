package org.hzz.weak;

import org.hzz.User;

import java.lang.ref.WeakReference;

public class WeakMain {
    public static void main(String[] args) throws InterruptedException {
        WeakReference<User> userWeakReference = new WeakReference(new User());
        System.out.println(userWeakReference.get()); // org.hzz.User@1b6d3586
        System.gc();
        Thread.sleep(2000);
        System.out.println(userWeakReference.get()); // null
    }
}
