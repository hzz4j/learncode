package org.hzz.aop.interceptor.impl;

import org.hzz.aop.advice.BeforeAdvice;
import org.hzz.aop.interceptor.MethodInterceptor;
import org.hzz.aop.interceptor.MethodInvocation;

public class MethodBeforeAdviceInterceptor implements MethodInterceptor {
    private BeforeAdvice advice;

    public MethodBeforeAdviceInterceptor(BeforeAdvice advice){
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation mi) {
        advice.before();
        return mi.proceed();
    }
}
