package org.hzz.clone.deep.clone;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class City implements Cloneable{
    private String name;

    @Override
    public City clone() throws CloneNotSupportedException {
        return (City)super.clone();
    }
}
