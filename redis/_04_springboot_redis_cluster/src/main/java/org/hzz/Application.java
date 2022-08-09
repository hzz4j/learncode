package org.hzz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        stringRedisTemplate.opsForValue().set("springboot:hzz","Hello q10viking");
        String s = stringRedisTemplate.opsForValue().get("springboot:hzz");
        System.out.println(s);
    }
}
