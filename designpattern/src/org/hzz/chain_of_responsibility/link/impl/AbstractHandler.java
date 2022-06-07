package org.hzz.chain_of_responsibility.link.impl;

import org.hzz.chain_of_responsibility.link.Handler;
import org.hzz.chain_of_responsibility.link.Request;

public abstract class AbstractHandler implements Handler{
    private boolean isSuccess;

    /**
     * @param request
     * @return 是否成功
     */
    protected abstract boolean doProcess(Request request);

    @Override
    public void process(Request request) {
        this.isSuccess = doProcess(request);
    }

    @Override
    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    public void failInfo() {
        System.out.println(this.getClass().getSimpleName()+" failed");
    }
}