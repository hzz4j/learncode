package org.hzz.aop.advice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractAdvice implements Advice{
    public Object invokeAdviceMethod(Object obj, Method method,Object[] args){
        try {
            method.setAccessible(true);
            return method.invoke(obj,args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
