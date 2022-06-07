package org.hzz.aop.adapter;

import org.hzz.aop.advice.Advice;
import org.hzz.aop.interceptor.MethodInterceptor;
import org.hzz.aop.interceptor.MethodInvocation;

import java.util.ArrayList;
import java.util.List;

/**
 * 适配
 */
public class AdviceAdapterRegistry {
    private static final List<Adpater> adpaters = new ArrayList<>();

    static{
        registerAdviceAdapter(new BeforeAdviceAdapter());
        registerAdviceAdapter(new AfterReturingAdviceAdapter());
    }

    private static void registerAdviceAdapter(Adpater adpater) {
        adpaters.add(adpater);
    }

    public static MethodInterceptor adapterAdivceToInvocation(Advice advice){

        if(advice instanceof MethodInterceptor){
           return (MethodInterceptor) advice;
        }

        for (Adpater adpater: adpaters){
            if(adpater.support(advice)){
                return adpater.getInterceptor(advice);
            }
        }
        throw new IllegalArgumentException(advice.toString());
    }

}
