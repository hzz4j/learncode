package com.tuling;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

@SPI
public interface Car {

    @Adaptive
    String getCarName(URL url);

    String sayHell();


}
