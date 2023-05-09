package org.hzz;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.hzz.car.Car;

public class SPIMain {
    public static void main(String[] args) {
        ExtensionLoader<Car> extensionLoader = ExtensionLoader.getExtensionLoader(Car.class);
        Car car = extensionLoader.getExtension("black");
        car.name();
    }
}
/**output
 * I am a car wrapper
 * I am a black car
 */