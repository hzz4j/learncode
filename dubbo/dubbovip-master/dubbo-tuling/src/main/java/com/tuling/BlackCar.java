package com.tuling;

import org.apache.dubbo.common.URL;

public class BlackCar implements Car {

    @Override
    public String getCarName(URL url) {
        return "black";
    }

    @Override
    public String sayHell() {
        return null;
    }
}
