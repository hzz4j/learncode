package org.hzz;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        final UserService target = new UserService();
        Enhancer enhancer = new Enhancer();
        enhancer.setUseCache(false);
        enhancer.setSuperclass(UserService.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                if(method.getName().equals("test")){
                    System.out.println("before");
//                    Object result = method.invoke(target, objects);
                    Object result = methodProxy.invoke(target, objects);
                    System.out.println("after");
                    return result;
                }
                return method.invoke(target, objects);
            }
        });

        UserService proxy = (UserService) enhancer.create();
        proxy.test();
    }
}
/**
 * before
 * test
 * after
 */