package org.hzz.aop.adapter;

import org.hzz.aop.advice.Advice;
import org.hzz.aop.interceptor.MethodInterceptor;

public interface Adpater {
    boolean support(Advice advice);
    MethodInterceptor getInterceptor(Advice advice);
}
