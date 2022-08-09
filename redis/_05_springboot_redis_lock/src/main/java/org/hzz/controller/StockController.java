package org.hzz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {
    private final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private Object lock = new Object();
    // http://localhost:8080/deduck_stock
    @GetMapping("deduck_stock")
    public String deduckStock(){
        // 在redis中它的库存设置为300
        final String key = "stock:product:1";
        synchronized (lock){
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get(key));
            if(stock>0){
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set(key,String.valueOf(realStock));
                logger.info("扣减库存成功,剩余："+realStock);
            }else{
                logger.info("扣减库存失败");
            }
        }
        return "Finished";
    }
}
