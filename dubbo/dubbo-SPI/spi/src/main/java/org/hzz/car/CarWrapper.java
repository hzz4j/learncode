package org.hzz.car;

import org.apache.dubbo.common.URL;

public class CarWrapper implements Car{
    private Car car;

    public CarWrapper(Car car) {
        this.car = car;
    }

    @Override
    public void name(URL url) {
        System.out.println("I am a car wrapper");
        car.name(url);
    }
}
