package org.hzz;

import org.hzz.executor.SimpleExecutor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

import org.hzz.executor.Executor;
import org.hzz.plugin.Interceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PluginApplication implements CommandLineRunner, ApplicationContextAware {
	private ApplicationContext applicationContext;

	/**
	 * 添加拦截器
	 * @param interceptors
	 * @return
	 */
	@Bean
	public Executor simpleExecutor(List<Interceptor> interceptors){
		Executor target = new SimpleExecutor();
		for (Interceptor interceptor : interceptors) {
			target = (Executor)interceptor.plugin(target);
		}
		return target;
	}


	public static void main(String[] args) {
		System.out.println("Java版本："+System.getProperty("java.version"));
		SpringApplication.run(PluginApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Executor executor = applicationContext.getBean("simpleExecutor",Executor.class);
		executor.executor();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
