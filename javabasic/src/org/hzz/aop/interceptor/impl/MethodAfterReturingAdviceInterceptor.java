package org.hzz.aop.interceptor.impl;

import org.hzz.aop.advice.AfterReturningAdvice;
import org.hzz.aop.interceptor.MethodInterceptor;
import org.hzz.aop.interceptor.MethodInvocation;

public class MethodAfterReturingAdviceInterceptor implements MethodInterceptor {
    private AfterReturningAdvice advice;

    public MethodAfterReturingAdviceInterceptor(AfterReturningAdvice advice){
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation mi) {
        Object proceed = mi.proceed();
        advice.afterRetuing();
        return proceed;
    }
}
