package org.hzz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching  // 开启基于注解的缓存
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
