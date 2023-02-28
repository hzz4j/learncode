package org.hzz.decorator.component;

import org.hzz.decorator.Decorator;

public class ConcreteComponent implements Component{
    @Override
    public void operation() {
        System.out.println("拍照.");
    }
}
