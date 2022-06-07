package org.hzz.chain_of_responsibility.link.impl;

import org.hzz.chain_of_responsibility.link.Request;

public class PermitsHandler extends AbstractHandler{

    @Override
    protected boolean doProcess(Request request) {
        System.out.println("访问权限");
        return request.isPermits();
    }
}
