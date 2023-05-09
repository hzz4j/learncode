package org.hzz;

public class GreetingServiceImpl implements GreetingService{
    @Override
    public String sayHi(String name) {
        return "Hi, " + name + " (Hello World)";
    }
}
