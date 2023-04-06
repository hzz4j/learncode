package org.hzz.plugin;

public interface Interceptor {
    Object intercept(Invocation invocation) throws Throwable;
    
    default Object plugin(Object target){
        return Plugin.wrap(target, this);
    }
}
