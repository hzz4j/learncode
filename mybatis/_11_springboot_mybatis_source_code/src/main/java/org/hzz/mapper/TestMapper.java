package org.hzz.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.hzz.entity.Test;

import java.util.List;

@Mapper
public interface TestMapper {

    @Select("select 1")
    Integer test1();

    @Insert("insert into test values(#{a}, #{b})")
    void insert(@Param("a") String a, @Param("b") String b);

    @Select("select * from test")
    List<Test> selectAll();

}
