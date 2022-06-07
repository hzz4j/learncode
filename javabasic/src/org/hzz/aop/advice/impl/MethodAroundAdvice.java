package org.hzz.aop.advice.impl;

import org.hzz.aop.advice.AbstractAdvice;
import org.hzz.aop.advice.Advice;
import org.hzz.aop.interceptor.MethodInterceptor;
import org.hzz.aop.interceptor.MethodInvocation;

import java.lang.reflect.Method;

public class MethodAroundAdvice extends AbstractAdvice implements MethodInterceptor,Advice {
    private Object target;
    private Method method;

    public MethodAroundAdvice(Object target, Method method){
        this.target = target;
        this.method = method;
    }


    @Override
    public Object invoke(MethodInvocation mi) {
        return super.invokeAdviceMethod(target, method, new Object[]{mi});
    }
}
