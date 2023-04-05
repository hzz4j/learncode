package org.hzz;

import org.apache.ibatis.annotations.Mapper;
import org.hzz.mapper.TestMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan(basePackages = "org.hzz.mapper", annotationClass = Mapper.class)
public class Application implements CommandLineRunner {
    private TestMapper testMapper;
    public Application(TestMapper testMapper){
        this.testMapper = testMapper;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
//        System.out.println(testMapper.test1());
//        testMapper.insert("Source Code", "Q10Viking");
        testMapper.selectAll().stream().forEach(System.out::println);
//        testMapper.selectAll();
    }
}
