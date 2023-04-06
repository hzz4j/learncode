package org.hzz.interceptor;

import org.hzz.plugin.Interceptor;
import org.hzz.plugin.Invocation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class TwoInterceptor implements Interceptor{

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
       System.out.println("OneInterceptor");
       return invocation.proceed();
    }
}
