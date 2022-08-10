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

@Service
public class ProductService {
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

    public Product getProduct(Integer id){
        String productKey = RedisKeyPrefixConst.PRODUCT_CACHE+id;
        Product product = null;
        product = JSON.parseObject(redisUtil.get(productKey), Product.class);
        if(product!=null){
            logger.info("从缓存中获取");
            return product;
        }
        logger.info("从DB获取");
        product = productMapper.selectById(id);

        // 加入缓存
        redisUtil.set(productKey,JSON.toJSONString(product));
        return product;
    }
}
