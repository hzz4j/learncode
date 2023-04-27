package org.hzz.lock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractLock implements Lock{
    @Override
    public void lock() {
        if(tryLock()){
            log.info("获取锁");
        }else{
            waitLock();
            lock();
        }
    }


    protected abstract boolean tryLock();

    protected abstract void waitLock();

    @Override
    public abstract void unlock();
}
