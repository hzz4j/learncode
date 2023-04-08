package org.hzz;

public class ThreadResolveResult implements ResolveResult{
    private volatile int result = 0;
    private Thread thread;

    public ThreadResolveResult(ResolveResult resolveResult){
        thread = new Thread(()-> this.result = resolveResult.resolve());
        thread.start();
    }
    @Override
    public int resolve() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return result;
    }
}
