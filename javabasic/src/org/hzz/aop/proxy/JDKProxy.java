package org.hzz.aop.proxy;

import org.hzz.aop.interceptor.Chain;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxy implements InvocationHandler {
    public ProxyFactory factory;

    public JDKProxy(ProxyFactory factory){
        this.factory = factory;
    }

    public Object getProxy(){
        Object target = factory.getTarget();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                interfaces,this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 构建责任链
        Chain chain = new Chain(factory.getTarget(),method,
                factory.getMethodInterceptors(),args);
        Object result = chain.proceed();

        return result;
    }


}
