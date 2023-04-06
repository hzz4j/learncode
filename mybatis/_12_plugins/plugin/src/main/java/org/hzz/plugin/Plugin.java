package org.hzz.plugin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.hzz.annotation.Intercepts;
import org.hzz.annotation.Signature;
import org.hzz.plugin.exception.PluginException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@AllArgsConstructor
public class Plugin implements InvocationHandler{

    private Object target;
    private Interceptor interceptor;
    private Map<Class<?>,Set<Method>> signatureMap;


    public static Object wrap(Object target, Interceptor interceptor) {
        Class<?> type = target.getClass();
        Map<Class<?>,Set<Method>> signatureMap = getSignatureMap(interceptor);
        Class<?>[] interfaces = getAllInterfaces(type, signatureMap);

        if(interfaces.length == 0){
            return target;
        }else{
            return Proxy.newProxyInstance(
                    type.getClassLoader(),
                    interfaces,
                    new Plugin(target, interceptor, signatureMap));
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            Set<Method> methods = signatureMap.get(method.getDeclaringClass());
            if (methods != null && methods.contains(method)) {
                return interceptor.intercept(new Invocation(target, method, args));
            }
            return method.invoke(target, args);
        } catch (Exception e) {
            throw e;
        }
    }

    public static Class<?>[] getAllInterfaces(Class<?> target, Map<Class<?>,Set<Method>> signatureMap){
        Set<Class<?>> interfaces = new HashSet<Class<?>>();
        while(target != null){
            for(Class<?> i : target.getInterfaces()){
                if(signatureMap.containsKey(i)){
                    interfaces.add(i);
                }
            }
            target = target.getSuperclass();
        }
        return interfaces.toArray(new Class<?>[interfaces.size()]);
    }


    public static Map<Class<?>,Set<Method>> getSignatureMap(Interceptor interceptor){
        Intercepts interceptsAnnotation = interceptor.getClass().getAnnotation(Intercepts.class);
        if (interceptsAnnotation == null) {
            throw new PluginException("No @Intercepts annotation was found in interceptor " + interceptor.getClass().getName());
        }
        Signature[] sigs = interceptsAnnotation.value();
        Map<Class<?>,Set<Method>> signatureMap = new HashMap();

        for(Signature sig : sigs){
            Set<Method> methods = signatureMap.computeIfAbsent(sig.type(), k -> new HashSet<Method>());
            try{
             Method m = sig.type().getMethod(sig.method(), sig.args());
             methods.add(m);
            }catch(NoSuchMethodException e){
                throw new PluginException("could not find method on " + sig.type() + " named " + sig.method() + " with parameters " + sig.args(), e);
            }   
        }
        log.info(signatureMap.toString());
        return signatureMap;
    }
}
