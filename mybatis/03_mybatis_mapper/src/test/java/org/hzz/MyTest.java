package org.hzz;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hzz.mapper.EmpMapper;
import org.hzz.pojo.Emp;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MyTest {
    private static final String configFile = "mybatis-config.xml";
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() throws IOException {
        InputStream in = Resources.getResourceAsStream(configFile);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
    }

    @Test
    public void selectTest(){
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
            Emp emp = mapper.selectEmp(5);
            System.out.println(emp);
        }
    }

    @Test
    public void insert(){
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
            Emp emp = new Emp();
            emp.setUsername("静默");
            mapper.insertEmp(emp);
            sqlSession.commit();
            // 会赋值到emp.id中
            System.out.println(emp); // Emp{id=4, username='静默'}
        }
    }

    @Test
    public void insertHasSelectKet(){
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
            Emp emp = new Emp();
            emp.setUsername("静默");
            mapper.insertEmpHasSelectKey(emp);
            sqlSession.commit();
            // 会赋值到emp.id中
            System.out.println(emp); // Emp{id=4, username='静默'}
        }
    }
}
