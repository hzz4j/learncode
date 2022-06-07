package org.hzz.aop.proxy;

import org.hzz.aop.interceptor.MethodInterceptor;

public class ProxyFactory {
    private Object target;
    private MethodInterceptor[] methodInterceptors;

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setMethodInterceptors(MethodInterceptor[] methodInterceptors) {
        this.methodInterceptors = methodInterceptors;
    }

    public Object getTarget() {
        return target;
    }

    public MethodInterceptor[] getMethodInterceptors() {
        return methodInterceptors;
    }
}
