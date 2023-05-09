package org.hzz.car;

import org.apache.dubbo.common.extension.SPI;

@SPI // 1. @SPI标注在接口上，表示该接口是一个扩展点
public interface Car {
    void name();
}
