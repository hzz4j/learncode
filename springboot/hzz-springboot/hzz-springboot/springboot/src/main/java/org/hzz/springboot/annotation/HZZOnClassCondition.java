package org.hzz.springboot.annotation;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

public class HZZOnClassCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        MultiValueMap<String, Object> annotationAttributes = metadata.getAllAnnotationAttributes(HZZConditionalOnClass.class.getName());
        String className = (String) annotationAttributes.getFirst("value");
        System.out.println("准备加载类：" + className);
        try {
            context.getClassLoader().loadClass(className);
            System.out.println("加载类成功：" + className);
            return true;
        } catch (ClassNotFoundException e) {
            System.out.println("加载类失败：" + className);
            return false;
        }
    }
}
