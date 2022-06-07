package org.hzz.aop.interceptor;

import java.lang.reflect.Method;

/**
 * 责任链调用
 */
public class Chain implements MethodInvocation {
    private Object target;
    private Method method;
    private Object[] args;

    private MethodInterceptor[] methodInterceptors;
    private int currrentIndex = 0;


    public Chain(Object target,Method method,MethodInterceptor[] methodInterceptors,Object[] args){
        this.target = target;
        this.method = method;
        this.methodInterceptors = methodInterceptors;
        this.args = args;
    }

    @Override
    public Object proceed(){
        if(currrentIndex == methodInterceptors.length){
            try {
                method.setAccessible(true);
                return method.invoke(this.target,this.args);
            }catch (Exception e){
                System.out.println(e);
            }
        }

        return methodInterceptors[currrentIndex++].invoke(this);
    }

}
