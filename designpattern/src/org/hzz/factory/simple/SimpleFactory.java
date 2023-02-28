package org.hzz.factory.simple;

import org.hzz.factory.product.Product;
import org.hzz.factory.product.ProductA;
import org.hzz.factory.product.ProductB;

public class SimpleFactory {
    public static Product createProduct(String type){
        if("0".equals(type)){
            return new ProductA();
        }else if ("1".equals(type)){
            return new ProductB();
        }else{
            return null;
        }
    }
}
