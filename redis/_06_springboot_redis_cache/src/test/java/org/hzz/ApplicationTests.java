package org.hzz;

import com.alibaba.fastjson.JSON;
import org.hzz.entity.Product;
import org.hzz.util.RedisKeyPrefixConst;
import org.hzz.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void contextLoads() {

        String s = redisUtil.get(RedisKeyPrefixConst.PRODUCT_CACHE + 2);
        Product product = JSON.parseObject(s,Product.class);
        System.out.println(product);
    }

}
