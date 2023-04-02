package org.hzz._02_springbeanlifecycle.processor;

import org.hzz._02_springbeanlifecycle.life.UserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class UserBeanPostProcessor implements InstantiationAwareBeanPostProcessor, MergedBeanDefinitionPostProcessor, BeanPostProcessor {
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if("userService".equals(beanName)){
            System.out.println("实例化前: InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation");
        }
        return null;
    }


    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if("userService".equals(beanName)){
            System.out.println("实例化后: InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation");
        }
        return true;
    }

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        if("userService".equals(beanName)){
            System.out.println("merged bean definition: MergedBeanDefinitionPostProcessor#postProcessMergedBeanDefinition");
        }
    }


    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if("userService".equals(beanName)){
            UserService userService = (UserService) bean;
            System.out.println("已经完成属性注入" + userService.getMsg()+ " | InstantiationAwareBeanPostProcessor#postProcessProperties");
        }
        return pvs;
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if("userService".equals(beanName)){
            System.out.println("初始化前: BeanPostProcessor#postProcessBeforeInitialization");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if("userService".equals(beanName)){
            System.out.println("初始化后: BeanPostProcessor#postProcessAfterInitialization");
        }
        return bean;
    }
}
