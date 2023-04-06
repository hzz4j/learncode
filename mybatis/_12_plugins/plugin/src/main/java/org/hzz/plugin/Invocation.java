package org.hzz.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Invocation {
    private Object target;
    private Method method;
    private Object[] args;

    public Object proceed() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        return method.invoke(target, args);
    }
}
