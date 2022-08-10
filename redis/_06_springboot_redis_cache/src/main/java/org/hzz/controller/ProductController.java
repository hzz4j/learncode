package org.hzz.controller;

import org.hzz.entity.Product;
import org.hzz.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public String create(@RequestBody Product product){
        productService.createProduct(product);
        return "create success";
    }

    @PostMapping("update")
    public String update(@RequestBody Product product){
        productService.updateProduct(product);
        return "update success";
    }

    @GetMapping("/v1/get")
    public Product getV1(Integer id){
        Product product = productService.getProductV1(id);
        return product;
    }

    @GetMapping("/v2/get")
    public Product getV2(Integer id){
        Product product = productService.getProductV2(id);
        return product;
    }
}
