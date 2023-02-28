package org.hzz.strategy.v3;

public class User{
    public String name;
    public int age;
    public int height;

    public User(String name, int age,int height) {
        this.name = name;
        this.age = age;
        this.height = height;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                '}';
    }
}
