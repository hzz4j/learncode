package org.hzz.strategy.v3;

import java.util.Arrays;
import java.util.Comparator;

public class JDKSort {
    public static void main(String[] args) {
        User[] users = new User[]{
                new User("Dally",23,172),
                new User("Jack",28,167),
                new User("Sunny",20,176)
        };

        Comparator<User> sortByAge = (u1, u2)-> u1.age - u2.age;
        Comparator<User> sortByHeight = (u1,u2) -> u1.height - u2.height;
        System.out.println("---------------按身高排序-----------------------");
        Arrays.sort(users,sortByAge);
        System.out.println(Arrays.toString(users));

        System.out.println("---------------按身高排序-----------------------");
        Arrays.sort(users,sortByHeight);
        System.out.println(Arrays.toString(users));
    }
}
/**
 * ---------------按身高排序-----------------------
 * [User{name='Sunny', age=20, height=176}, User{name='Dally', age=23, height=172}, User{name='Jack', age=28, height=167}]
 * ---------------按身高排序-----------------------
 * [User{name='Jack', age=28, height=167}, User{name='Dally', age=23, height=172}, User{name='Sunny', age=20, height=176}]
 */