package org.hzz;

import lombok.extern.slf4j.Slf4j;
import org.hzz.domain.dto.UserDTO;
import org.hzz.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Date;

@SpringBootTest
@Slf4j
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void save(){
        UserDTO userDTO = UserDTO.builder()
                .username("")
                .password("Root.123456")
                .email("1193094618@qq.com")
                .created(new Date())
                .build();
        int r = userService.save(userDTO);
        log.info("成功插入:{}",r);
    }
}
