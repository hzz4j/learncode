package org.hzz.aop.service;

public class UserSrv implements IUserSrv{
    @Override
    public void hello() {
        System.out.println("User say hello");
    }
}
