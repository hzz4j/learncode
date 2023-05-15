package org.hzz.clone.shallow;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Cloneable{
    public String name;
    public int age;

    // 重写clone方法
    @Override
    public User clone() throws CloneNotSupportedException {
        return (User)super.clone();
    }
}
