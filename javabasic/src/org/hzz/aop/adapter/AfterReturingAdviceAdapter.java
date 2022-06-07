package org.hzz.aop.adapter;

import org.hzz.aop.advice.Advice;
import org.hzz.aop.advice.AfterReturningAdvice;
import org.hzz.aop.interceptor.MethodInterceptor;
import org.hzz.aop.interceptor.impl.MethodAfterReturingAdviceInterceptor;

public class AfterReturingAdviceAdapter implements Adpater {
    @Override
    public boolean support(Advice advice) {
        return advice instanceof AfterReturningAdvice;
    }

    @Override
    public MethodInterceptor getInterceptor(Advice advice) {
        return new MethodAfterReturingAdviceInterceptor((AfterReturningAdvice)advice);
    }
}
