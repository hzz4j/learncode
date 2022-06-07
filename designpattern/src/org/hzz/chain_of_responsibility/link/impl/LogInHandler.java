package org.hzz.chain_of_responsibility.link.impl;

import org.hzz.chain_of_responsibility.link.Request;

public class LogInHandler extends AbstractHandler{
    @Override
    protected boolean doProcess(Request request) {
        System.out.println("登录验证");
        return request.isLoggedOn();
    }
}
