package org.hzz.decorator;

import org.hzz.decorator.component.Component;

public abstract class Decorator implements Component{
    protected Component component;

    public Decorator(Component component){
        this.component = component;
    }
}
