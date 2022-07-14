package com.tuling;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.utils.ConcurrentHashSet;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Protocol;

import java.util.List;
import java.util.ServiceLoader;

public class SpiTest {
    public static void main(String[] args) {

//        ServiceLoader<Car> cars = ServiceLoader.load(Car.class);
//        for (Car car : cars) {
//            System.out.println(car.getCarName(null));
//        }


//        ExtensionLoader<Protocol> extensionLoader = ExtensionLoader.getExtensionLoader(Protocol.class);
//        Protocol protocol = extensionLoader.getExtension("http");
//        System.out.println(protocol);

//        ExtensionLoader<Car> extensionLoader = ExtensionLoader.getExtensionLoader(Car.class);
//
//        Car car = extensionLoader.getExtension("true"); // 自动注入，AOP
//
//        System.out.println(car.getCarName());

        ExtensionLoader<Person> extensionLoader = ExtensionLoader.getExtensionLoader(Person.class);
        Person person = extensionLoader.getExtension("black");  // BlackPerson

        URL url = new URL("x", "localhost", 8080);
        url = url.addParameter("car", "black");

        System.out.println(person.getCar().getCarName(url));  // 代理逻辑


//        System.out.println(person.getCar().getCarName(url));


//        ExtensionLoader<Filter> extensionLoader = ExtensionLoader.getExtensionLoader(Filter.class);
//        URL url = new URL("http://", "localhost", 8080);
//        url = url.addParameter("cache", "test");
//        List<Filter> activateExtensions = extensionLoader.getActivateExtension(url, new String[]{"validation"}, CommonConstants.CONSUMER);
//        for (Filter activateExtension : activateExtensions) {
//            System.out.println(activateExtension);
//        }


//        ConcurrentHashSet set = new ConcurrentHashSet();
//        set.add("周瑜1");
//        set.add("周瑜2");
//        set.add("周瑜3");
//
//        System.out.println(set);
    }
}
