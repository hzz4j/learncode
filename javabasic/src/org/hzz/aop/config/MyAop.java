package org.hzz.aop.config;

import org.hzz.aop.annotations.After;
import org.hzz.aop.annotations.AfterReturning;
import org.hzz.aop.annotations.Around;
import org.hzz.aop.annotations.Before;
import org.hzz.aop.interceptor.MethodInvocation;

public class MyAop {
    @AfterReturning
    public void afterReturning(){System.out.println("---------------afterReturning-----------------");}
    @Before
    public void before(){ System.out.println("---------------before-----------------");}
    @After
    public void after(){ System.out.println("--------------after---------------------------");}
    @Around
    public void around(MethodInvocation proceedingJoinpoint){
        System.out.println("--------------around start---------------------------");
        proceedingJoinpoint.proceed();
        System.out.println("--------------around after---------------------------");
    }
    public void test(){System.out.println("not run");}
}
