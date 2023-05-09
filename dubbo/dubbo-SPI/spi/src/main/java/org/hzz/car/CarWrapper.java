package org.hzz.car;

public class CarWrapper implements Car{
    private Car car;

    public CarWrapper(Car car) {
        this.car = car;
    }

    @Override
    public void name() {
        System.out.println("I am a car wrapper");
        car.name();
    }
}
