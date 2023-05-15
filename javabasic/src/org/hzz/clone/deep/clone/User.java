package org.hzz.clone.deep.clone;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Cloneable{
    private String name;
    private City city;

    @Override
    public User clone() throws CloneNotSupportedException {
        User user =  (User)super.clone();
        user.setCity(city.clone());
        return user;
    }
}
