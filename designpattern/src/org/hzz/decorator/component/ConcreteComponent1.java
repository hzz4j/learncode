package org.hzz.decorator.component;

import org.hzz.decorator.Decorator;

public class ConcreteComponent1 extends Decorator{
    public ConcreteComponent1(Component component) {
        super(component);
    }

    @Override
    public void operation() {
        System.out.println("美颜.");
        this.component.operation();
    }
}
