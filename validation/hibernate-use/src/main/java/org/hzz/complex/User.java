package org.hzz.complex;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class User {

    @NotNull(message = "用户ID不能为空")
    private String userId;
    @NotNull
    private String userName;
    private String password;
    private String email;
    private String phone;
    private Date birthday;
    private List<User> friends;
}
