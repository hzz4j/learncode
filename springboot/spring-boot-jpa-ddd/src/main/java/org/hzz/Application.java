package org.hzz;

import org.hzz.entity.Book;
import org.hzz.entity.Student;
import org.hzz.repository.StudentCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.UUID;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.hzz.repository")
@EntityScan(basePackages = "org.hzz.entity")
public class Application implements CommandLineRunner {
    @Value("${hzz.use}")
    private String databaseName;

    @Autowired
    private StudentCrudRepository studentCrudRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hzz Use: " + databaseName);
        System.out.println("增加学生信息：");
        Student student = new Student();
        student.setId(UUID.randomUUID().toString());
        student.setName("hzz");
        student.setGrade("六年级");
        student.addBook(new Book("领域驱动设计", "Eric Evans"));
        student.addBook(new Book("Java编程思想", "Bruce Eckel"));
        student.addBook(new Book("Java并发编程实战", "Brian Goetz"));

        student.addHobby("篮球");
        student.addHobby("足球");
        student.addHobby("乒乓球");
        studentCrudRepository.save(student);
        System.out.println("查询所有学生信息：");
        studentCrudRepository.findAll().forEach(System.out::println);
    }
}
