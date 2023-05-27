package org.hzz.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StudentJdbcRepository {
    private JdbcTemplate jdbcTemplate;

    public StudentJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Student findById(Long id) {
        return jdbcTemplate.queryForObject("select * from student where id=?",
                new BeanPropertyRowMapper<>(Student.class), id);
    }
}
