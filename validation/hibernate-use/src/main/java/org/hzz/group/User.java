package org.hzz.group;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class User {
    public interface LoginGroup{}
    public interface RegisterGroup{}

    @NotNull(message = "用户ID不能为空", groups = {LoginGroup.class})
    private String userId;

    // 需要验证
    @NotBlank(message = "用户名不能为空",groups = {LoginGroup.class,RegisterGroup.class})
    // 不能写成
    //@NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "邮箱不能为空", groups = {RegisterGroup.class})
    private String email;
}
