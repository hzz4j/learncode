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
    public void test01(){
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
            // 虽然代码中查询了两次，但是实际上只从数据库查询了一次
            List<Dept> depts = mapper.selectDept(null);
            List<Dept> depts2 = mapper.selectDept(null);
        }
    }

    /**
     * 一级缓存的作用域是在sqlSession
     * 不同的sqlSession会导致缓存失效
     */
    @Test
    public void test02(){
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
            // 虽然代码中查询了两次，但是实际上只从数据库查询了一次
            List<Dept> depts = mapper.selectDept(null);
        }

        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
            List<Dept> depts2 = mapper.selectDept(null);
        }
    }

    @Test
    public void test03(){
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
            Dept dept1 = new Dept();
            dept1.setDeptId(1);
            mapper.selectDept(dept1);

            Dept dept2 = new Dept();
            //dept2.setDeptId(1);  // 会走一级缓存
            dept2.setDeptName("经理"); // 缓存失效
            mapper.selectDept(dept2);
        }
    }



    @Test
    public void test04(){
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
            Dept dept1 = new Dept();
            dept1.setDeptId(1);
            mapper.selectDept(dept1);

            // 执行了增删改的操作
            EmpMapper empMapper = sqlSession.getMapper(EmpMapper.class);
            Emp emp = new Emp();
            emp.setUsername("cache");
            empMapper.insertEmp(emp);

            Dept dept2 = new Dept();
            dept2.setDeptId(1);
            mapper.selectDept(dept2);
            sqlSession.commit();

        }
    }


    @Test
    public void test05(){
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


    @Test
    public void test06(){
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
            Dept dept1 = new Dept();
            dept1.setDeptId(1);
            mapper.selectDept(dept1);

            // 事务提交，存储二级缓存
            sqlSession.commit();

            // 执行了增删改的操作 Emp里面有cache-ref
            EmpMapper empMapper = sqlSession.getMapper(EmpMapper.class);
            Emp emp = new Emp();
            emp.setUsername("cache");
            empMapper.insertEmp(emp);
            sqlSession.commit(); // 需要提交

            Dept dept2 = new Dept();
            dept2.setDeptId(1);
            mapper.selectDept(dept2);

        }
    }

}
