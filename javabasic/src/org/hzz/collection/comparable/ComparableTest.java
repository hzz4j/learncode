package org.hzz.collection.comparable;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;

public class ComparableTest {
    public static void main(String[] args) {
        Dog[] dogs = new Dog[]{
                new Dog("老旺财", 10),
                new Dog("小旺财", 3),
                new Dog("二旺财", 5),
        };

        Arrays.sort(dogs);
        System.out.println(Arrays.toString(dogs));
    }
}
/**
 * [Dog(name=小旺财, age=3), Dog(name=二旺财, age=5), Dog(name=老旺财, age=10)]
 */
