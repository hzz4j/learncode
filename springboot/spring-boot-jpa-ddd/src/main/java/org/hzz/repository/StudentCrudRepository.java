package org.hzz.repository;

import org.hzz.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCrudRepository extends JpaRepository<Student, String> {
}
