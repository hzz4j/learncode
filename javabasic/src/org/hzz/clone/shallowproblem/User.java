package org.hzz.clone.shallowproblem;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Cloneable{
    private String name;
    private City city;

    @Override
    public User clone() throws CloneNotSupportedException {
        return (User)super.clone();
    }
}
