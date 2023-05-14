package org.hzz.complex;

import javax.validation.Valid;

public class UserService implements IUserService{

    public UserService() {
    }

    public UserService(@Valid User user) {
    }

    // 直接在方法里面加@Valid也是可以的，这里主要
    // 这里主要是想试一下如果在接口中加了，还会不会生效
    @Override
    public void saveUser(User user) {

    }

    @Override
    public User getUser() {
        User user = new User();
        user.setUserName("Q10Viking");
        return user;
    }

}
