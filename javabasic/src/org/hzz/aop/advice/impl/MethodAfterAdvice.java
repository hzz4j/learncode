package org.hzz.aop.advice.impl;

import org.hzz.aop.advice.AbstractAdvice;
import org.hzz.aop.advice.Advice;
import org.hzz.aop.interceptor.MethodInterceptor;
import org.hzz.aop.interceptor.MethodInvocation;

import java.lang.reflect.Method;

public class MethodAfterAdvice extends AbstractAdvice implements MethodInterceptor, Advice {
    private Object target;
    private Method method;

    public MethodAfterAdvice(Object target, Method method){
        this.target = target;
        this.method = method;
    }


    @Override
    public Object invoke(MethodInvocation mi) {
        try{
            return mi.proceed();
        }finally {
            invokeAdviceMethod(target,method,null);
        }
    }
}
