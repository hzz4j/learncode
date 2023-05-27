package org.hzz;

import lombok.extern.slf4j.Slf4j;
import org.hzz.student.StudentJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {
    @Autowired
    private StudentJdbcRepository studentJdbcRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Student id 10001 -> {}", studentJdbcRepository.findById(10001L));
    }
}
