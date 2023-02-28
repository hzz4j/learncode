package org.hzz.factory.abstractfactory;

import org.hzz.factory.product.Product;

public class MainTest {
    public static void main(String[] args) {
        Application app = new ConcreteProductB();
        Product product = app.getObject();
        System.out.println(product);
    }
}
