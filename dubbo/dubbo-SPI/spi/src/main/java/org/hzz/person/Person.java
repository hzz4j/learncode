package org.hzz.person;

import org.apache.dubbo.common.extension.SPI;
import org.hzz.car.Car;

@SPI
public interface Person {
    Car getCar();
}
