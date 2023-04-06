package org.hzz.interceptor;

import org.hzz.plugin.Interceptor;
import org.hzz.plugin.Invocation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class OneInterceptor implements Interceptor{

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
       System.out.println("OneInterceptor");
       return invocation.proceed();
    }

}
