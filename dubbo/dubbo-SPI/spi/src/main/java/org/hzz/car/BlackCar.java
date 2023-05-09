package org.hzz.car;

import org.apache.dubbo.common.URL;

public class BlackCar implements Car{
    @Override
    public void name(URL url) {
        System.out.println("I am a black car");
    }
}
