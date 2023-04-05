package org.hzz.plugin;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;


@Component
@Intercepts({
//        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class,Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class,Object.class,
                RowBounds.class, ResultHandler.class}),
        @Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class}),
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class,ResultHandler.class}),

})
public class ExamPlugin implements Interceptor {

    public ExamPlugin(){
        System.out.println("ExamPlugin");
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println(invocation.getTarget());
        System.out.println(Arrays.toString(invocation.getArgs()));
        System.out.println(invocation.getMethod());
        System.out.println("-----------------------------------");
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
