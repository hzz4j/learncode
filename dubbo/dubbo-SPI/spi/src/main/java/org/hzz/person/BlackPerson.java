package org.hzz.person;


import org.hzz.car.Car;

public class BlackPerson implements Person{
    private Car car;    // Adaptive代理类

    public void setCar(Car car){
        this.car = car;
    }
    @Override
    public Car getCar() {
        System.out.println("I am a black person");
        return this.car;
    }
}
