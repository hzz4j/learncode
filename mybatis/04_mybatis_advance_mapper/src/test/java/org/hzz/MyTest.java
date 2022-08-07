package org.hzz;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hzz.mapper.DeptMapper;
import org.hzz.mapper.EmpMapper;
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
    public void selectEmpAndDept(){
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
            List<EmpDeptDTO> empDeptDTOS = mapper.selectEmpAndDept();
            System.out.println(empDeptDTOS);
        }
    }

    @Test
    public void selectEmpById(){
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
            Emp emp = mapper.selectEmpById(1);
            System.out.println(emp);
        }
    }

    @Test
    public void selectEmpAndDeptUseAssociation(){
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
            List<EmpDeptDTO> empDeptDTOS = mapper.selectEmpAndDeptUseAssociation();
            System.out.println(empDeptDTOS);
        }
    }

    @Test
    public void selectDeptAndEmp(){
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
            List<DeptEmpDTO> deptEmpDTOS = mapper.selectDeptAndEmp();
            System.out.println(deptEmpDTOS);
        }
    }

    @Test
    public void nestingQuery(){
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
            List<DeptEmpDTO> deptEmpDTOS = mapper.nestingQuery();
            System.out.println(deptEmpDTOS);
        }
    }

}
