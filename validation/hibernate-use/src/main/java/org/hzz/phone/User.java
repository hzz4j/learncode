package org.hzz.phone;

import lombok.Data;

@Data
public class User {

    @MyPhone
    private String phonenumber;
}
