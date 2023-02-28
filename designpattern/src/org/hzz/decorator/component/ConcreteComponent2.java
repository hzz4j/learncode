package org.hzz.decorator.component;

import org.hzz.decorator.Decorator;

public class ConcreteComponent2 extends Decorator{
    public ConcreteComponent2(Component component) {
        super(component);
    }

    @Override
    public void operation() {
        System.out.println("滤镜.");
        this.component.operation();
    }
}
