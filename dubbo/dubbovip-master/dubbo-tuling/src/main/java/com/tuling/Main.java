package com.tuling;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("com.tuling")
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Main.class);
        RedCar redCar = applicationContext.getBean("redCar", RedCar.class);
        redCar.sayHell();
        System.out.println(redCar);

    }
}
