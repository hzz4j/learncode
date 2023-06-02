package org.hzz.usercreation.entity.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.hzz.usercreation.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonUser implements User {
    private String name;
    private String password;

    @Override
    public boolean passwordIsValid() {
        return password != null && password.length() > 5;
    }
}
