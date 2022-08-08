package com.tuling;

import com.tuling.entity.User;
import com.tuling.mapper.UserMapper;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.List;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class App {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        //将XML配置文件构建为Configuration配置类
        Reader reader = Resources.getResourceAsReader(resource);
        // 通过加载配置文件流构建一个SqlSessionFactory   解析xml文件  1
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        // 数据源 执行器  DefaultSqlSession 2
        SqlSession session = sqlSessionFactory.openSession();
        try {
            // 执行查询 底层执行jdbc 3
            User user =  session.selectOne("com.tuling.mapper.UserMapper.selectById", 1);

            // 创建动态代理
           /* UserMapper mapper = session.getMapper(UserMapper.class);
            System.out.println(mapper.getClass());
            User user = mapper.selectById(1);*/
            System.out.println(user.getUserName());

            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        } finally {
            session.close();
        }
    }



}
