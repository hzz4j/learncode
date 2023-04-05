package org.hzz._02_springbeanlifecycle.life;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService implements BeanNameAware, InitializingBean , DisposableBean {

    @Autowired
    @Qualifier("systemProperties")
    private Map<String, Object> systemProperties;

    @Value("${my.msg}")
    private String msg;
    public UserService(){
        System.out.println("构造方法");
    }

    @PostConstruct
    public void test(){
        System.out.println("@PostConstruct");
    }


    public String getMsg(){
        return this.msg;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("Aware bean name: "+name);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean#afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean#destroy");
    }


    @PreDestroy
    public void predestroy(){
        System.out.println("@PreDestory");
    }


    public Map<String,Object> getSystemProperties(){
        return this.systemProperties;
    }
}
