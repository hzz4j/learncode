package org.hzz.service;

import com.alibaba.fastjson.JSON;
import org.hzz.entity.Product;
import org.hzz.mapper.ProductMapper;
import org.hzz.util.RedisKeyPrefixConst;
import org.hzz.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class ProductService {
    // 缓存的操作时间
    public static final Integer PRODUCT_CACHE_TIMEOUT = 60 * 60 * 24;
    private Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private RedisUtil redisUtil;

    public void createProduct(Product product){
        productMapper.createProduct(product);
    }

    public void updateProduct(Product product){
        productMapper.updateProduct(product);
    }

    /**-------------------------------------------------------------------------------
     *    简单版redis+db
     *-------------------------------------------------------------------------------*/
    public Product getProductV1(Integer id){
        String productKey = RedisKeyPrefixConst.PRODUCT_CACHE+id;
        Product product = null;
        // 从redis中获取
        product = JSON.parseObject(redisUtil.get(productKey), Product.class);
        if(product!=null){
            logger.info("从缓存中获取");
            return product;
        }
        logger.info("从DB获取");
        product = productMapper.selectById(id);

        // 加入缓存 并且有过期时间
        redisUtil.set(productKey,JSON.toJSONString(product),PRODUCT_CACHE_TIMEOUT, TimeUnit.SECONDS);
        return product;
    }


    /**-------------------------------------------------------------------------------
     *    简单版redis+db 冷热分离
     *-------------------------------------------------------------------------------*/
    public Product getProductV2(Integer id){
        String productKey = RedisKeyPrefixConst.PRODUCT_CACHE+id;
        Product product = null;
        // 从redis中获取
        product = JSON.parseObject(redisUtil.get(productKey), Product.class);
        if(product!=null){
            // 冷热分离，延长过期时间
            redisUtil.expire(productKey,genProductCahceTimeout(),TimeUnit.SECONDS);
            logger.info("从缓存中获取，并延长过期时间");
            return product;
        }
        logger.info("从DB获取");
        product = productMapper.selectById(id);

        // 加入缓存 并且有过期时间
        redisUtil.set(productKey,JSON.toJSONString(product),PRODUCT_CACHE_TIMEOUT, TimeUnit.SECONDS);
        return product;
    }


    /**-------------------------------------------------------------------------------
     *    简单版redis+db + 冷热分离 + 缓存击穿（失效）
     *-------------------------------------------------------------------------------*/
    public Product getProductV3(Integer id){
        String productKey = RedisKeyPrefixConst.PRODUCT_CACHE+id;
        Product product = null;
        // 从redis中获取
        product = JSON.parseObject(redisUtil.get(productKey), Product.class);
        if(product!=null){
            // 冷热分离，延长过期时间
            redisUtil.expire(productKey,genProductCahceTimeout(),TimeUnit.SECONDS);
            logger.info("从缓存中获取，并延长过期时间");
            return product;
        }
        logger.info("从DB获取");
        product = productMapper.selectById(id);

        // 加入缓存 并且设置随机的过期时间
        redisUtil.set(productKey,JSON.toJSONString(product),genProductCahceTimeout(), TimeUnit.SECONDS);
        return product;
    }

    public int genProductCahceTimeout(){
        // 在24小时基础上，延长0-4小时
        return PRODUCT_CACHE_TIMEOUT + new Random().nextInt(5)*60*60;
    }
}
