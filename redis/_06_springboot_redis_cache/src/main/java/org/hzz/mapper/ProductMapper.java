package org.hzz.mapper;

import org.apache.ibatis.annotations.Param;
import org.hzz.entity.Product;

public interface ProductMapper {
    Product selectById(Integer id);
    void createProduct(@Param("product") Product product);
    void updateProduct(@Param("product") Product product);
}
