package org.hzz;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MainInterface {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setUseCache(false);
        enhancer.setSuperclass(UserServiceInterface.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                if(method.getName().equals("test")){
                    System.out.println("执行切面逻辑");
                }
                return null;
            }
        });

        Object proxy = enhancer.create();
        ((UserServiceInterface)proxy).test();
    }
}
/**
 * 执行切面逻辑
 */