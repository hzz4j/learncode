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

public class SecondTest {
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
            Emp emp = mapper.selectEmp(1);
            System.out.println(emp);
        }
    }
}
