package org.hzz.netty;

import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public abstract class SingleThreadEventExecutor  extends AbstractEventExecutor implements Executor {
    public static final int DEFAULT_MAX_PENDING_TASKS = 2;
    // 任务队列允许最大的任务数
    private final int maxPendingTasks;
    private final Queue<Runnable> taskQueue;
    private volatile Thread thread;
    private final Executor executor;
    private static final int ST_NOT_STARTED = 1;
    private static final int ST_STARTED = 2;
    private volatile int state = ST_NOT_STARTED;
    private static final AtomicIntegerFieldUpdater<SingleThreadEventExecutor> STATE_UPDATER =
            AtomicIntegerFieldUpdater.newUpdater(SingleThreadEventExecutor.class, "state");

    public SingleThreadEventExecutor(Executor executor) {
        this(DEFAULT_MAX_PENDING_TASKS,executor);
    }

    public SingleThreadEventExecutor(int maxPendingTasks,Executor executor) {
        this.maxPendingTasks = Math.max(2,maxPendingTasks);
        this.taskQueue = newTaskQueue(this.maxPendingTasks);
        this.executor = ThreadExecutorMap.apply(executor,this);
    }

    protected Queue<Runnable> newTaskQueue(int maxPendingTasks){
        if(maxPendingTasks <= 0){
            throw new IllegalArgumentException("maxPendingTasks: " + maxPendingTasks + " (expected: > 0)");
        }
        return new LinkedBlockingQueue<Runnable>(maxPendingTasks);
    }

    protected void addTask(Runnable task){
        if(!offerTask(task)){
            System.out.println("任务队列繁忙，拒绝接收任务："+task);
        }
    }

    final boolean offerTask(Runnable task){
        return taskQueue.offer(task);
    }

    final Runnable pollTask(){
        // taskQueue.take(); 会阻塞
        return taskQueue.poll();    // 从队列中取出任务不会阻塞
    }

    protected void runAllTask(){
        for(;;){
            Runnable task = pollTask();
            if(task == null){
                break;
            }
            task.run();
        }
    }


    @Override
    public boolean inEventLoop(Thread thread) {
        return thread == this.thread;
    }

    @Override
    public void execute(Runnable command) {
        addTask(command);
        if(!inEventLoop()){
            startThread();
        }
    }


    private void startThread() {
        if(state == ST_NOT_STARTED){
            if(STATE_UPDATER.compareAndSet(this,ST_NOT_STARTED,ST_STARTED)){
                boolean success = false;
                try {
                    doStartThread();
                    success = true;
                }finally {
                    if(!success){
                        STATE_UPDATER.compareAndSet(this,ST_STARTED,ST_NOT_STARTED);
                    }
                }
            }
        }
    }

    private void doStartThread(){
        assert thread == null;
        executor.execute(()->{
            thread = Thread.currentThread();
            try {
                SingleThreadEventExecutor.this.run();
            }finally {
                thread = null;
            }
        });
    }

    protected abstract void run();
}
