package org.hzz.aop.factory;

import org.hzz.aop.advice.Advice;
import org.hzz.aop.advice.impl.MethodAfterAdvice;
import org.hzz.aop.advice.impl.MethodAfterReturningAdvice;
import org.hzz.aop.advice.impl.MethodAroundAdvice;
import org.hzz.aop.advice.impl.MethodBeforeAdvice;
import org.hzz.aop.annotations.After;
import org.hzz.aop.annotations.AfterReturning;
import org.hzz.aop.annotations.Around;
import org.hzz.aop.annotations.Before;
import org.hzz.aop.utils.AdviceUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AdviceFactory {
    private static Map<Class<? extends Annotation>,AspectJAnnotationType> annotationTypeMap =  new HashMap<>(8);

    protected enum AspectJAnnotationType {
        AtPointcut, AtAround, AtBefore, AtAfter, AtAfterReturning, AtAfterThrowing
    }

    static {
        annotationTypeMap.put(Before.class,AspectJAnnotationType.AtBefore);
        annotationTypeMap.put(After.class,AspectJAnnotationType.AtAfter);
        annotationTypeMap.put(AfterReturning.class,AspectJAnnotationType.AtAfterReturning);
        annotationTypeMap.put(Around.class,AspectJAnnotationType.AtAround);
    }

    private Object obj;
    public AdviceFactory(Object obj){
        this.obj = obj;
    }

    private static final Class<? extends Annotation>[] ASPECTJ_ANNOTATION_CLASSES = new Class[]{
            Before.class,After.class,AfterReturning.class, Around.class
    };

    private AspectJAnnotationType findAspectJAnnotationOnMethod(Method method){
        Annotation annotation = findAnnotationOnMethod(method);
        if(annotation != null){
            return annotationTypeMap.get(annotation.annotationType());
        }
        return null;
    }

    public static Annotation findAnnotationOnMethod(Method method){
        for (Class clazz:
                ASPECTJ_ANNOTATION_CLASSES) {
            Annotation annotation = AdviceUtil.findAnnotaion(method, (Class<Annotation>) clazz);
            if(annotation != null){
                return annotation;
            }
        }
        return null;
    }

    public Advice getAdvice(Method method){
        AspectJAnnotationType type = findAspectJAnnotationOnMethod(method);
        Advice advice = null;
        switch (type){
            case AtBefore:
                advice = new MethodBeforeAdvice(obj,method);
                break;
            case AtAfter:
                advice = new MethodAfterAdvice(obj,method);
                break;
            case AtAfterReturning:
                advice = new MethodAfterReturningAdvice(obj,method);
                break;
            case AtAround:
                advice = new MethodAroundAdvice(obj,method);
                break;
            default:
                throw new UnsupportedOperationException(
                        "Unsupported advice type on method: " + method);
        }

        return advice;
    }
}
