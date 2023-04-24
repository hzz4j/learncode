package org.hzz.service;

import lombok.extern.slf4j.Slf4j;
import org.hzz.entity.User;
import org.hzz.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserMapper userMapper;
    public List<User> getUser(String name){
        return userMapper.selectUser(name);
    }

    public int insertUser(String name, String password){
        redisTemplate.opsForValue().set(name, password);
        log.info("redis set key: {}, value: {}", name, password);
        return userMapper.insertUser(name, password);
    }
}
