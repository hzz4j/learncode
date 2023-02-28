package org.hzz.factory.abstractfactory;

import org.hzz.factory.product.Product;

public abstract class Application {
    // 工厂方法
    public abstract Product createProduct();

    public Product getObject(){
        Product product = createProduct();
        // do something else...
        return product;
    }
}
