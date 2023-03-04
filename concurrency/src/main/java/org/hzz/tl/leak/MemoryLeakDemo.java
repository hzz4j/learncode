package org.hzz.tl.leak;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MemoryLeakDemo {
    private static final int TASK_SIZE = 500;
    /**线程池*/
    final static Executor excutor = new ThreadPoolExecutor(5,5,1,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>());

    private ThreadLocal<LocalVariable> threadLocal;

    static class LocalVariable{
        private byte[] a = new byte[5*1024*1024]; // 5M
    }
    public static void main(String[] args) {
        for (int i=0; i<TASK_SIZE;i++){
            excutor.execute(()->{
                MemoryLeakDemo oom = new MemoryLeakDemo();
                oom.threadLocal = new ThreadLocal<>();
                oom.threadLocal.set(new LocalVariable());
                // 线程池的线程不会结束，所以需要手动释放空间
                oom.threadLocal.remove();
            });
        }
    }
}
