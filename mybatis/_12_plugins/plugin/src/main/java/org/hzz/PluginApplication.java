package org.hzz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

import org.hzz.executor.Executor;
import org.hzz.plugin.Interceptor;

@SpringBootApplication
public class PluginApplication implements CommandLineRunner{

	@Autowired
	private List<Interceptor> interceptors;

	@Autowired
	@Qualifier("simpleExecutor")
	private Executor executor;

	public static void main(String[] args) {
		System.out.println("Java版本："+System.getProperty("java.version"));
		SpringApplication.run(PluginApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Executor target = executor;
		for (Interceptor interceptor : interceptors) {
			target = (Executor)interceptor.plugin(target);
		}

		target.executor();
	}

}
