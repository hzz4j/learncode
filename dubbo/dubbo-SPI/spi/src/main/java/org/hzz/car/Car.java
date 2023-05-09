package org.hzz.car;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

@SPI // 1. @SPI标注在接口上，表示该接口是一个扩展点
public interface Car {

    @Adaptive // 2. @Adaptive标注在方法上，表示该方法是一个自适应扩展点
    void name(URL url);
}

