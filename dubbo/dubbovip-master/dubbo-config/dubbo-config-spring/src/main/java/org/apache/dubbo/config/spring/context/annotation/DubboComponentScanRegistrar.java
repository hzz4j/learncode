/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.config.spring.context.annotation;

import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor;
import org.apache.dubbo.config.spring.beans.factory.annotation.ServiceAnnotationBeanPostProcessor;
import org.apache.dubbo.config.spring.util.BeanRegistrar;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

/**
 * Dubbo {@link DubboComponentScan} Bean Registrar
 *
 * @see Service
 * @see DubboComponentScan
 * @see ImportBeanDefinitionRegistrar
 * @see ServiceAnnotationBeanPostProcessor
 * @see ReferenceAnnotationBeanPostProcessor
 * @since 2.5.7
 */
public class DubboComponentScanRegistrar implements ImportBeanDefinitionRegistrar {


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        System.out.println("执行DubboComponentScanRegistrar");

        // 拿到DubboComponentScan注解所定义的包路径，扫描该package下的类，识别这些类上
        Set<String> packagesToScan = getPackagesToScan(importingClassMetadata);

        // 注册ServiceAnnotationBeanPostProcessor一个Bean
        // 实现了BeanDefinitionRegistryPostProcessor接口，所以在Spring启动时会调用postProcessBeanDefinitionRegistry方法
        // 该方法会进行扫描，扫描@Service注解了的类，然后生成BeanDefinition（会生成两个，一个普通的bean，一个ServiceBean），后续的Spring周期中会生成Bean
        // 在ServiceBean中会监听ContextRefreshedEvent事件，一旦Spring启动完后，就会进行服务导出
        registerServiceAnnotationBeanPostProcessor(packagesToScan, registry);

        // 注册ReferenceAnnotationBeanPostProcessor
        // 实现了AnnotationInjectedBeanPostProcessor接口，继而实现了InstantiationAwareBeanPostProcessorAdapter接口
        // 所以Spring在启动时，在对属性进行注入时会调用AnnotationInjectedBeanPostProcessor接口中的postProcessPropertyValues方法
        // 在这个过程中会按照@Reference注解的信息去生成一个RefrenceBean对象
        registerReferenceAnnotationBeanPostProcessor(registry);

    }

    /**
     * Registers {@link ServiceAnnotationBeanPostProcessor}
     *
     * @param packagesToScan packages to scan without resolving placeholders
     * @param registry       {@link BeanDefinitionRegistry}
     * @since 2.5.8
     */
    private void registerServiceAnnotationBeanPostProcessor(Set<String> packagesToScan, BeanDefinitionRegistry registry) {
        // 生成一个RootBeanDefinition，对应的beanClass为ServiceAnnotationBeanPostProcessor.class
        BeanDefinitionBuilder builder = rootBeanDefinition(ServiceAnnotationBeanPostProcessor.class);
        // 将包路径作为在构造ServiceAnnotationBeanPostProcessor时调用构造方法时的传入参数
        builder.addConstructorArgValue(packagesToScan);
        builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);

    }

    /**
     * Registers {@link ReferenceAnnotationBeanPostProcessor} into {@link BeanFactory}
     *
     * @param registry {@link BeanDefinitionRegistry}
     */
    private void registerReferenceAnnotationBeanPostProcessor(BeanDefinitionRegistry registry) {

        // Register @Reference Annotation Bean Processor
        // 注册一个ReferenceAnnotationBeanPostProcessor做为bean，ReferenceAnnotationBeanPostProcessor是一个BeanPostProcessor
        BeanRegistrar.registerInfrastructureBean(registry,
                ReferenceAnnotationBeanPostProcessor.BEAN_NAME, ReferenceAnnotationBeanPostProcessor.class);

    }

    private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                metadata.getAnnotationAttributes(DubboComponentScan.class.getName()));
        String[] basePackages = attributes.getStringArray("basePackages");
        Class<?>[] basePackageClasses = attributes.getClassArray("basePackageClasses");
        String[] value = attributes.getStringArray("value");
        // Appends value array attributes
        Set<String> packagesToScan = new LinkedHashSet<String>(Arrays.asList(value));
        packagesToScan.addAll(Arrays.asList(basePackages));
        for (Class<?> basePackageClass : basePackageClasses) {
            packagesToScan.add(ClassUtils.getPackageName(basePackageClass));
        }
        if (packagesToScan.isEmpty()) {
            return Collections.singleton(ClassUtils.getPackageName(metadata.getClassName()));
        }
        return packagesToScan;
    }

}
