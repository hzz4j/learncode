package org.hzz;


import lombok.extern.slf4j.Slf4j;
import org.hzz.domain.entity.UserDO;
import org.hzz.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        UserDO userDO = userMapper.selectById(1220708537638920191l);
        log.info(userDO.toString());
    }
}
