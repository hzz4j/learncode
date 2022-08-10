package org.hzz.service;

import org.hzz.entity.Product;
import org.hzz.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public void createProduct(Product product){
        productMapper.createProduct(product);
    }

    public void updateProduct(Product product){
        productMapper.updateProduct(product);
    }

    public Product getProduct(Integer id){
        return productMapper.selectById(id);
    }
}
