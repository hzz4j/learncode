package org.hzz.chain_of_responsibility.link.impl;

import org.hzz.chain_of_responsibility.link.Request;

public class SensitiveWordsHandler extends AbstractHandler{
    @Override
    protected boolean doProcess(Request request) {
        System.out.println("敏感词过滤");
        return request.isContainsSensitiveWords();
    }
}
