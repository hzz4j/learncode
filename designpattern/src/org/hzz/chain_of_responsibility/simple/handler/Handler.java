package org.hzz.chain_of_responsibility.simple.handler;

import org.hzz.chain_of_responsibility.simple.Request;

public abstract class Handler {
    private Handler next;

    public Handler(Handler next){
        this.next = next;
    }

    public abstract boolean process(Request request);
    public Handler getNext() {
        return next;
    }

    public void setNext(Handler next) {
        this.next = next;
    }
}
