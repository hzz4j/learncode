package org.hzz;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hzz.mapper.DeptMapper;
import org.hzz.mapper.EmpMapper;
import org.hzz.pojo.Dept;
import org.hzz.pojo.DeptEmpDTO;
import org.hzz.pojo.Emp;
import org.hzz.pojo.EmpDeptDTO;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTest {
    private static final String configFile = "mybatis-config.xml";
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() throws IOException {
        InputStream in = Resources.getResourceAsStream(configFile);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
    }

    @Test
    public void test(){
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
            Dept dept1 = new Dept();
            dept1.setDeptId(1);
            mapper.selectDept(dept1);

            // 存储二级缓存
            sqlSession.commit();

            Dept dept2 = new Dept();
            dept2.setDeptId(1);
            mapper.selectDept(dept2); // 会走二级缓存
        }
    }

}
