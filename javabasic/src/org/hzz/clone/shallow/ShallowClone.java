package org.hzz.clone.shallow;

public class ShallowClone {
    public static void main(String[] args) throws CloneNotSupportedException {
       User user = new User("张三", 18);
       User user2 = user.clone();
       user2.setName("Q10Viking");
        System.out.println(user);
        System.out.println(user2);
    }
}
/**
 * User(name=张三, age=18)
 * User(name=Q10Viking, age=18)
 */