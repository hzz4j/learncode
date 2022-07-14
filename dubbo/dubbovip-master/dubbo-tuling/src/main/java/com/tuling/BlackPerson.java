package com.tuling;

import org.apache.dubbo.common.URL;

public class BlackPerson implements Person {

    private Car car;   // Adaptive(代理)

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public Car getCar() {
        return car;
    }
}
