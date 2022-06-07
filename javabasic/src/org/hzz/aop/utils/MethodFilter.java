package org.hzz.aop.utils;

import java.lang.reflect.Method;

@FunctionalInterface
public interface MethodFilter {
    boolean match(Method method);
}
