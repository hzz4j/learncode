package org.hzz.aop.utils;

import org.hzz.aop.adapter.AdviceAdapterRegistry;
import org.hzz.aop.advice.Advice;
import org.hzz.aop.annotations.*;
import org.hzz.aop.factory.AdviceFactory;
import org.hzz.aop.interceptor.MethodInterceptor;
import org.hzz.aop.utils.compare.Converter;
import org.hzz.aop.utils.compare.ConvertingComparator;
import org.hzz.aop.utils.compare.InstanceComparator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AdviceUtil {
    private static final Comparator<Method> adviceMethodComparator;

    static{
        adviceMethodComparator = new ConvertingComparator(
                new InstanceComparator(Around.class,Before.class, After.class, AfterReturning.class),
                (Converter<Method,Annotation>) AdviceFactory::findAnnotationOnMethod
        );
    }


    /**
     * Action to take on each method.
     */
    @FunctionalInterface
    public interface MethodCallback {

        /**
         * Perform an operation using the given method.
         * @param method the method to operate on
         */
        void doWith(Method method);
    }

    public static void doWithMethods(Method[] methods, MethodCallback callback, MethodFilter filter){
        for (Method method:
             methods) {
            if(filter.match(method)){
                callback.doWith(method);
            }
        }
    }

    public static <A extends Annotation> A findAnnotaion(AnnotatedElement annotatedElement, Class<A> target) {
        Annotation result = null;
        for(Annotation annotation: getAnnotationsNotIncludeJava(annotatedElement)){
            if(target.isAssignableFrom(annotation.getClass())){
                result = annotation;
            }else{
                result = findAnnotaion(annotation.annotationType(),target);
            }
        }
        return (A) result;
    }

    // 排除@Target,@Document,@Retention 这三个类
    private static List<Annotation> getAnnotationsNotIncludeJava(AnnotatedElement annotatedElement){
        Predicate<Annotation> notIncludeJava = e -> !e.annotationType().getName().startsWith("java");
        return Arrays.stream(annotatedElement.getAnnotations())
                .filter(notIncludeJava)
                .collect(Collectors.<Annotation>toList());
    }

    // 适配
    public static MethodInterceptor[] getMethodInterceptors(Object o){
        List<MethodInterceptor> interceptors = new ArrayList<>();
        Advice[] advices = getAdvices(o);
        for (Advice advice: advices){
            MethodInterceptor interceptor = AdviceAdapterRegistry.adapterAdivceToInvocation(advice);
            interceptors.add(interceptor);
        }
        return interceptors.toArray(new MethodInterceptor[interceptors.size()]);
    }



    private static Advice[] getAdvices(Object o) {
        List<Method> methodList = getAdviceMethod(o);
        // 排序
        methodList.sort(adviceMethodComparator);

        Advice[] advices = new Advice[methodList.size()];
        int i = 0;

        AdviceFactory adviceFactory = new AdviceFactory(o);

        for(Method method: methodList){
            Advice advice = adviceFactory.getAdvice(method);
            advices[i++] = advice;
        }
        return advices;
    };

    private static List<Method> getAdviceMethod(Object o){
        Method[] declaredMethods = o.getClass().getDeclaredMethods();
        List<Method> methodList = new ArrayList<>();
        // 找到所有的@Before,@After,@Around等标注的注解,为了方便，这里我设计了@Parent.
        MethodFilter filter = method -> AdviceUtil.findAnnotaion(method,Parent.class) != null;

        AdviceUtil.doWithMethods(declaredMethods,methodList::add,filter);

//        Util.doWithMethods(declaredMethods,methodList::add,
//                MyAopTestMain::isFindMethodOfParentAnnotation);  // 做作弊
        return methodList;
    }


    private static boolean isFindMethodOfParentAnnotation(Method method){
        Parent parent = AdviceUtil.findAnnotaion(method,Parent.class);
        return parent == null;
    }
}
