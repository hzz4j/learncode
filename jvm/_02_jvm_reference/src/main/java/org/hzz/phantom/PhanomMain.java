package org.hzz.phantom;

import org.hzz.User;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class PhanomMain {
    public static void main(String[] args) throws InterruptedException {
        ReferenceQueue queue = new ReferenceQueue<>();
        PhantomReference<User> phantomReference = new PhantomReference<>(new User(),queue);
        // 当调用get方法返回的是null
        System.out.println(phantomReference.get());
        System.out.println(queue.poll());
        System.gc();
        Thread.sleep(2000);
        System.out.println(phantomReference.get());
        System.out.println(queue.poll());
    }
}
/**
 * null
 * null
 * null
 * java.lang.ref.PhantomReference@1b6d3586
 */