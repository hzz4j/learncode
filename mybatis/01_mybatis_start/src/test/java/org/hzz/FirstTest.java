package org.hzz;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hzz.pojo.Emp;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class FirstTest {

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
            Emp emp = (Emp) sqlSession.selectOne("org.hzz.mapper.EmpMapper.selectEmp",1);
            System.out.println(emp);
        }
    }

    @Test
    public void insertTest(){
        try(SqlSession session = sqlSessionFactory.openSession(true)){
            Emp emp = new Emp();
            emp.setUsername("hzz");
            Integer r = (Integer)session.insert("org.hzz.mapper.EmpMapper.insertEmp",emp);
            System.out.println(r);
        }
    }

    @Test
    public void updateTest(){
        try(SqlSession session = sqlSessionFactory.openSession(true)){
            Emp emp = new Emp();
            emp.setId(2);
            emp.setUsername("hzz01");
            Integer r = (Integer)session.insert("org.hzz.mapper.EmpMapper.updateEmp",emp);
            System.out.println(r);
        }
    }

    @Test
    public void deleteTest(){
        try(SqlSession session = sqlSessionFactory.openSession(true)){
            Integer r = (Integer)session.insert("org.hzz.mapper.EmpMapper.deleteEmp",2);
            System.out.println(r);
        }
    }
}
