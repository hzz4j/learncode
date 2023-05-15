package org.hzz.clone.deep.two;

public class DeepCloneTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        User user = new User("张三", new City("北京"));
        User user2 = CloneUtils.clone(user);

        user2.setName("Q10Viking");
        user2.getCity().setName("广州");
        System.out.println(user);
        System.out.println(user2);
    }
}
/**
 * User(name=张三, city=City(name=北京))
 * User(name=Q10Viking, city=City(name=广州))
 */