package org.hzz.usercreation.entity.impl;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.hzz.usercreation.entity.User;

@Value
@AllArgsConstructor
public class CommonUser implements User {
    private final String name;
    private final String password;

    @Override
    public boolean passwordIsValid() {
        return password != null && password.length() > 5;
    }
}
