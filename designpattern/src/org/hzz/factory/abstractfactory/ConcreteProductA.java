package org.hzz.factory.abstractfactory;

import org.hzz.factory.product.Product;
import org.hzz.factory.product.ProductA;
import org.hzz.factory.product.ProductB;

public class ConcreteProductA extends Application{
    @Override
    public Product createProduct() {
        return new ProductA();
    }
}
