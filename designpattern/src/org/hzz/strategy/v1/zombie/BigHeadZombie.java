package org.hzz.strategy.v1.zombie;

public class BigHeadZombie extends AbstractZombie{
    @Override
    public void display() {
        System.out.println("我是大头僵尸.");
    }

    @Override
    public void attack() {
        // do something else ...
        System.out.println("头撞.");
    }
}
