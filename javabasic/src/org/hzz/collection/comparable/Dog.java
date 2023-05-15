package org.hzz.collection.comparable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Dog implements Comparable<Dog> {
    private String name;
    private Integer age;
    @Override
    public int compareTo(Dog o) {
        return this.age.compareTo(o.age);
    }
}
