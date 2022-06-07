package org.hzz.aop.adapter;

import org.hzz.aop.advice.Advice;
import org.hzz.aop.advice.BeforeAdvice;
import org.hzz.aop.interceptor.MethodInterceptor;

public class AfterAdviceAdapter implements Adpater {
    @Override
    public boolean support(Advice advice) {
        return advice instanceof BeforeAdvice;
    }

    @Override
    public MethodInterceptor getInterceptor(Advice advice) {
        return null;
    }
}
