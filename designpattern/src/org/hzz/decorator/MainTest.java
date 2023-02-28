package org.hzz.decorator;

import org.hzz.decorator.component.Component;
import org.hzz.decorator.component.ConcreteComponent;
import org.hzz.decorator.component.ConcreteComponent1;
import org.hzz.decorator.component.ConcreteComponent2;

public class MainTest {
    public static void main(String[] args) {
        Component component = new ConcreteComponent2(
                new ConcreteComponent1(
                        new ConcreteComponent()
                )
        );
        component.operation();
    }
}
/**
 * 滤镜.
 * 美颜.
 * 拍照.
 */