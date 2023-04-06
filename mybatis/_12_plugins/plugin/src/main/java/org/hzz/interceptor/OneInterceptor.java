package org.hzz.interceptor;

import org.hzz.annotation.Intercepts;
import org.hzz.annotation.Signature;
import org.hzz.executor.Executor;
import org.hzz.plugin.Interceptor;
import org.hzz.plugin.Invocation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@Intercepts(
        @Signature(type = Executor.class, method = "executor", args = {})
)
public class OneInterceptor implements Interceptor{

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
       System.out.println("OneInterceptor");
       return invocation.proceed();
    }

}
