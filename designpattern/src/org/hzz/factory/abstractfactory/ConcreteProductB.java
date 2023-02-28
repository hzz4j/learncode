package org.hzz.factory.abstractfactory;

import org.hzz.factory.product.Product;
import org.hzz.factory.product.ProductB;

public class ConcreteProductB extends Application{
    @Override
    public Product createProduct() {
        return new ProductB();
    }
}
