package org.hzz.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    /**--------------------------------------------------------------------------------
     *      简单版setnx key value ex times
     *--------------------------------------------------------------------------------*/
    @GetMapping("/v1/deduck_stock")
    public String deduckStockV1(){
        // 在redis中它的库存设置为300
        final String key = "stock:product:1";
        final String lockKey = "lock:product:1";
        // 获取锁
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey,"true",1000, TimeUnit.MILLISECONDS);
        if(!result){
            return "error_code";
        }
        try{
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get(key));
            if(stock>0){
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set(key,String.valueOf(realStock));
                logger.info("扣减库存成功,剩余："+realStock);
            }else{
                logger.info("扣减库存失败");
            }
        }finally {
            // 释放锁
            stringRedisTemplate.delete(lockKey);
        }
        return "Finished";
    }


    /**--------------------------------------------------------------------------------
     *      简单版+UUID setnx key value ex times
     *--------------------------------------------------------------------------------*/
    @GetMapping("/v2/deduck_stock")
    public String deduckStockV2(){
        // 在redis中它的库存设置为300
        final String key = "stock:product:1";
        final String lockKey = "lock:product:1";
        final String clientId = UUID.randomUUID().toString();
        // 获取锁
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey,clientId,1000, TimeUnit.MILLISECONDS);
        if(!result){
            return "error_code";
        }
        try{
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get(key));
            if(stock>0){
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set(key,String.valueOf(realStock));
                logger.info("扣减库存成功,剩余："+realStock);
            }else{
                logger.info("扣减库存失败");
            }
        }finally {
            // 释放锁
            if(clientId.equals(stringRedisTemplate.opsForValue().get(lockKey))){
                stringRedisTemplate.delete(lockKey);
            }
        }
        return "Finished";
    }


    /**--------------------------------------------------------------------------------
     *      Redisson
     *--------------------------------------------------------------------------------*/
    @Autowired
    private Redisson redisson;
    @GetMapping("/v3/deduck_stock")
    public String deduckStockV3(){
        // 在redis中它的库存设置为300
        final String key = "stock:product:1";
        final String lockKey = "lock:product:1";
        RLock lock = redisson.getLock(lockKey);
        lock.lock();
        try{
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get(key));
            if(stock>0){
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set(key,String.valueOf(realStock));
                logger.info("扣减库存成功,剩余："+realStock);
            }else{
                logger.info("扣减库存失败");
            }
        }finally {
            // 释放锁
            lock.unlock();
        }
        return "Finished";
    }


}
