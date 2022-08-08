package org.hzz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sentinel")
public class SentinelController {
    private static final Logger logger =  LoggerFactory.getLogger(SentinelController.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 访问 http://localhost:8080/sentinel/test?name=hzz
    @GetMapping("/test")
    public void testSentinel(String name)  {
        int i = 0;
        while (true){
            try{
                redisTemplate.opsForValue().set("test:sentinel",name+"("+i+")");
                i++;
                logger.info("add success");
                Thread.sleep(2_000);
            }catch (Exception e){
                logger.info("error====>",e);
            }

        }
    }
}
