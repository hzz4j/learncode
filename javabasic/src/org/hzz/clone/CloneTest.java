package org.hzz.clone;

public class CloneTest {
    public static void main(String[] args) {
        // 等号赋值（ 基本类型）
        int number = 6;
        int number2 = number;
        // 修改 number2 的值
        number2 = 9;
        System.out.println("number：" + number);
        System.out.println("number2：" + number2);
        // 等号赋值（对象）
        Dog dog = new Dog();
        dog.name = "旺财";
        dog.age = 5;
        Dog dog2 = dog;
        // 修改 dog2 的值
        dog2.name = "大黄";
        dog2.age = 3;
        System.out.println(dog.name + "，" + dog.age + "岁");
        System.out.println(dog2.name + "，" + dog2.age + "岁");
    }
}
/**
 * number：6
 * number2：9
 * 大黄，3岁
 * 大黄，3岁
 */