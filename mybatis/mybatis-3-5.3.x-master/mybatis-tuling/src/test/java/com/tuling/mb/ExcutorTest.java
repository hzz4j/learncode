package com.tuling.mb;

import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.ReuseExecutor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class ExcutorTest {
    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    Connection connection;
    Transaction transaction;

    @Before
    public void before() throws Exception {
        String resource = "mybatis-config.xml";
        //将XML配置文件构建为Configuration配置类
        Reader reader = Resources.getResourceAsReader(resource);
        // 通过加载配置文件流构建一个SqlSessionFactory   解析xml文件  1
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        configuration = sqlSessionFactory.getConfiguration();
        connection = configuration.getEnvironment().getDataSource().getConnection();
        transaction = configuration.getEnvironment().getTransactionFactory().newTransaction(connection);
    }

    @Test
    public void simple() throws SQLException {

        SimpleExecutor simpleExecutor = new SimpleExecutor(configuration, transaction);

        MappedStatement mappedStatement = configuration.getMappedStatement("com.tuling.mapper.UserMapper.selectById");

        List<Object> list = simpleExecutor.doQuery(mappedStatement, 1,
                RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(1));
        list = simpleExecutor.doQuery(mappedStatement, 1,
                RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(1));

        System.out.println(list.get(0));


    }


    /**
     * 如果mysql驱动没开启预编译支持，其实预编译没意义
     * @throws SQLException
     */
    @Test
    public void reuse() throws SQLException {

        ReuseExecutor executor = new ReuseExecutor(configuration, transaction);

        MappedStatement mappedStatement = configuration.getMappedStatement("com.tuling.mapper.UserMapper.selectById");

        List<Object> list = executor.doQuery(mappedStatement, 1,
                RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(1));
        List<Object> list2 = executor.doQuery(mappedStatement, 1,
                RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(1));

        System.out.println(list.get(0));


    }


    @Test
    public void batch() throws SQLException {

        BatchExecutor executor = new BatchExecutor(configuration, transaction);

        MappedStatement mappedStatement = configuration.getMappedStatement("com.tuling.mapper.UserMapper.updateForName");

        Map map=new HashMap();
        map.put("arg0",1);
        map.put("arg1","xushu");

        int result=executor.doUpdate(mappedStatement, map);
        map.put("arg1","xs");
        int result2=executor.doUpdate(mappedStatement, map);
        List<BatchResult> batchResults = executor.doFlushStatements(false);
        System.out.println(batchResults.get(0).getUpdateCounts().length);


    }
}
