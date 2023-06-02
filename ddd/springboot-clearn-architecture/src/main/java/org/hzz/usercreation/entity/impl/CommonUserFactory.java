package org.hzz.usercreation.entity.impl;

import org.hzz.usercreation.entity.User;
import org.hzz.usercreation.entity.UserFactory;

public class CommonUserFactory implements UserFactory {
    @Override
    public User createUser(String name, String password) {
        return new CommonUser(name,password);
    }
}
