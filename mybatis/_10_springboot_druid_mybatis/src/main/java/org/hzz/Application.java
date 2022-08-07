package org.hzz;

import org.hzz.mapper.EmpMapper;
import org.hzz.pojo.Emp;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.hzz.mapper") // 扫描mapper
public class Application implements CommandLineRunner {
    @Autowired
    private EmpMapper empMapper;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Emp emp = empMapper.selectByPrimaryKey(1);
        System.out.println(emp);
    }
}
