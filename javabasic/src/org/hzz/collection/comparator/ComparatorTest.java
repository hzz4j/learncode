package org.hzz.collection.comparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ComparatorTest {
    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User("张三", 1000.0),
                new User("李四", 2000.0),
                new User("王五", 900.0)
        );

        // 外部比较器定义
        Comparator<User> comparator = (user1,user2)-> user1.getSalary().compareTo(user2.getSalary());
        users.sort(comparator);

        System.out.println(Arrays.toString(users.toArray()));
    }
}
/**
 * [User(name=王五, salary=900.0), User(name=张三, salary=1000.0), User(name=李四, salary=2000.0)]
 */