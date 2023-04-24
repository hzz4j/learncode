package org.hzz.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.hzz.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    @Insert("insert into user(name, password) values(#{name}, #{password})")
    int insertUser(String name, String password);

    @Select("select * from user where name = #{name}")
    List<User> selectUser(String name);
}
