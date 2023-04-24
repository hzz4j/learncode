package org.hzz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "org.hzz.mapper")
public class DockerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerServiceApplication.class, args);
    }

}
