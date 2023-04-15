package org.hzz.shutdown;

import lombok.extern.slf4j.Slf4j;
import org.hzz.promise.DefaultPromise;
import org.hzz.promise.Promise;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

@Slf4j
public class EventChild implements ShutDown,Runnable{
    private int id;
    private final Executor executor;
    /**-------------状态----------------------*/
    private final AtomicIntegerFieldUpdater<EventChild> STATE_UPDATE = AtomicIntegerFieldUpdater.newUpdater(
            EventChild.class, "state");
    private static final int ST_NOT_STARTED = 1;
    private static final int ST_STARTED = 2;
    private static final int ST_SHUTTING_DOWN = 3;
    private static final int ST_SHUTDOWN = 4;
    private static final int ST_TERMINATED = 5;
    private volatile int state = ST_NOT_STARTED;
    /**--------------状态---------------------*/
    private Thread selfThread;
    private Promise<?> terminationFuture = new DefaultPromise<Void>();

    public EventChild(Executor executor, int id){
        this.executor = executor;
        this.id = id;
        executor.execute(()->{
            if(STATE_UPDATE.compareAndSet(this,ST_NOT_STARTED,ST_STARTED)){
                log.info("EventChild [{}] state [ST_NOT_STARTED -> ST_STARTED] success",id);
                selfThread = Thread.currentThread();
                EventChild.this.run();
            }else{
                log.info("EventChild [{}] state [ST_NOT_STARTED -> ST_STARTED] failed",id);
            }
        });
    }


    @Override
    public Promise<?> shutdownGracefully() {
        if (STATE_UPDATE.compareAndSet(this, ST_STARTED, ST_SHUTTING_DOWN)) {
            log.info("EventChild [{}] state [ST_STARTED -> ST_SHUTTING_DOWN] success",id);
            //do something
        }else{
            log.info("EventChild [{}] state [ST_STARTED -> ST_SHUTTING_DOWN] failed",id);
        }
        return getTerminationFuture();
    }

    private boolean isShuttingDown(){
       return this.state == ST_SHUTTING_DOWN;
    }

    @Override
    public void run() {
        try{
            while (!isShuttingDown()){
                //do something
                try {
                    Thread.sleep(3000);
                    System.out.println("EventChild ["+id+"] is running");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }finally {
            if(STATE_UPDATE.compareAndSet(this,ST_SHUTTING_DOWN,ST_TERMINATED)){
                log.info("EventChild [{}] state [ST_SHUTTING_DOWN -> ST_TERMINATED] success",id);
            }else{
                log.info("EventChild [{}] state [ST_SHUTTING_DOWN -> ST_TERMINATED] failed",id);
            }
            terminationFuture.setSuccess(null);
        }
    }

    public Promise<?> getTerminationFuture(){
        return terminationFuture;
    }
}
