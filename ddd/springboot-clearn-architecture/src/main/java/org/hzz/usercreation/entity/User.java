package org.hzz.usercreation.entity;

public interface User {
    boolean passwordIsValid();
    String getPassword();
    String getName();
}
