package org.hzz.aop.advice.impl;

import org.hzz.aop.advice.AbstractAdvice;
import org.hzz.aop.advice.Advice;
import org.hzz.aop.advice.BeforeAdvice;

import java.lang.reflect.Method;

public class MethodBeforeAdvice extends AbstractAdvice implements BeforeAdvice{
    private Object target;
    private Method method;

    public MethodBeforeAdvice(Object target,Method method){
        this.target = target;
        this.method = method;
    }

    @Override
    public void before() {
        super.invokeAdviceMethod(target,method,null);
    }
}
