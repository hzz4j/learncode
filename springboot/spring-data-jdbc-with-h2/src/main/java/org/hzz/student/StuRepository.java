package org.hzz.student;


import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

@org.springframework.stereotype.Repository
public interface StuRepository  extends Repository<Student, Long> {
    Student findByName(String name);

    @Query("select * from student where name = :name")
    Student findStudentOnName(@Param("name") String name);
}
