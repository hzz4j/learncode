package org.hzz;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.hzz.car.Car;
import org.hzz.person.Person;

public class SPIMain {
    public static void main(String[] args) {
        ExtensionLoader<Person> extensionLoader = ExtensionLoader.getExtensionLoader(Person.class);
        Person person = extensionLoader.getExtension("black");
        URL url = new URL("dubbo", "localhost", 8080);
        url = url.addParameter("car", "black");
        person.getCar().name(url);
        System.out.println(person.getCar());
    }
}
/**output
 * I am a black person
 * I am a car wrapper
 * I am a black car
 * I am a black person
 * org.hzz.car.Car$Adaptive@180da663
 */