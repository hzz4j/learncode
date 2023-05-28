package org.hzz.controller;

import org.hzz.entity.Student;
import org.hzz.repository.StudentCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentCrudRepository studentCrudRepository;
    @GetMapping("/findAll")
    public List<Student> findAll(){
         return studentCrudRepository.findAll();
    }
}
