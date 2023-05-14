package org.hzz.basic;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class User {
    @NotNull(message = "用户ID不能为空")
    private String userId;
    @NotNull
    private String userName;

//    @Size(min = 1, max = 5, message = "用户密码长度必须在{min}和{max}之间")
//    private String somv = "123456789";
}
