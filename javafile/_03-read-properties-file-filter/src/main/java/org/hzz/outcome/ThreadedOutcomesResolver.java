package org.hzz.outcome;

public class ThreadedOutcomesResolver implements OutcomesResolver{
    private final Thread thread;
    private volatile boolean[] outcomes;
    public ThreadedOutcomesResolver(OutcomesResolver outcomesResolver) {
        this.thread = new Thread(()-> this.outcomes = outcomesResolver.resolveOutcomes());
        this.thread.start();
    }


    @Override
    public boolean[] resolveOutcomes() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return outcomes;
    }
}
