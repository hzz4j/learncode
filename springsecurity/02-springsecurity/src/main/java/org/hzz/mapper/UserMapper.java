package org.hzz.mapper;

import org.apache.ibatis.annotations.Select;
import org.hzz.bean.User;

public interface UserMapper {
    @Select("select * from tb_user where username=#{username}")
    User getByUsername(String username);
}
