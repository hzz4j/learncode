package org.hzz.aop.advice.impl;

import org.hzz.aop.advice.AbstractAdvice;
import org.hzz.aop.advice.AfterReturningAdvice;

import java.lang.reflect.Method;

public class MethodAfterReturningAdvice extends AbstractAdvice implements AfterReturningAdvice {
    private Object target;
    private Method method;

    public MethodAfterReturningAdvice(Object target, Method method){
        this.target = target;
        this.method = method;
    }

    @Override
    public void afterRetuing() {
        super.invokeAdviceMethod(target,method,null);
    }
}
