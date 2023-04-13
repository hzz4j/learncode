package org.hzz.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂
 */
public class DefaultThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolId = new AtomicInteger();
    private final AtomicInteger threadId = new AtomicInteger(0);
    private final String prefix;
    private final boolean daemon;
    private final int priority;
    private final ThreadGroup group;


    public DefaultThreadFactory(Class<?> poolType) {
        this(poolType, false, Thread.NORM_PRIORITY);
    }

    public DefaultThreadFactory(Class<?> poolType, boolean daemon,int priority) {
        this(toPoolName(poolType), daemon, Thread.NORM_PRIORITY);
    }

    public DefaultThreadFactory(String poolName, boolean daemon,int priority) {
       this(poolName,daemon,priority,Thread.currentThread().getThreadGroup());
    }

    public DefaultThreadFactory(String poolName, boolean daemon,int priority,ThreadGroup group) {
        this.prefix = poolName + '-' + poolId.incrementAndGet() + "-thread-";
        this.priority = priority;
        this.daemon = daemon;
        this.group = group;
    }

    public static String toPoolName(Class<?> poolType){
        if(poolType == null){
            throw new NullPointerException("poolType");
        }
        String simpleName = poolType.getSimpleName();
        if(Character.isUpperCase(simpleName.charAt(0)) && Character.isLowerCase(simpleName.charAt(1))){
            return Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
        }else{
            return simpleName;
        }
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, prefix + threadId.incrementAndGet());
        if(t.isDaemon() != daemon){
            t.setDaemon(daemon);
        }

        if(t.getPriority()!=priority){
            t.setPriority(priority);
        }
        return t;
    }
}
