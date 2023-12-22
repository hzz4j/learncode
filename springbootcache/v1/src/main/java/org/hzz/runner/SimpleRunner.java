package org.hzz.runner;

import lombok.extern.java.Log;
import org.hzz.Application;
import org.hzz.repository.MsgRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Log
public class SimpleRunner implements CommandLineRunner, ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Override
    public void run(String... args) throws Exception {
        log.info("-----------msg-------------------");
        /**
         * 因为spring的缓存是通过代理实现的，类似aop.被Spring包装了。
         * 在该类方法直接执行，不会走代理，所以我们需要拿到代理类
         */
        SimpleRunner simpleRunner = applicationContext.getBean(SimpleRunner.class);

        log.info(simpleRunner.getData(1));
        log.info(simpleRunner.getData(1));
        log.info(simpleRunner.getData(1));
    }

    private AtomicInteger count = new AtomicInteger(0);
    @Cacheable(cacheNames = "msg",key = "#root.targetClass.simpleName+':'+#id")
    public String getData(Integer id){
        return "msg"+count.incrementAndGet();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
