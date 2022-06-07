package org.hzz.chain_of_responsibility.link.impl;

import org.hzz.chain_of_responsibility.link.Request;

public class RequestFrequentAbstractHandler extends AbstractHandler {
    @Override
    protected boolean doProcess(Request request) {
        System.out.println("访问频率控制.");
        return request.isFrequentOk();
    }
}
