package org.hzz.aop.interceptor;

public interface MethodInterceptor {
    public Object invoke(MethodInvocation mi);
}
