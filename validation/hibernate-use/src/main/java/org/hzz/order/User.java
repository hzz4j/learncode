package org.hzz.order;

import lombok.Data;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

@Data
public class User {
    public interface LoginGroup{}
    public interface RegisterGroup{}

    // 组排序,先验证Default.class,再验证LoginGroup.class,最后验证RegisterGroup.class
    // 如果前面的验证失败,后面的验证不会执行
    @GroupSequence({
            Default.class,
            LoginGroup.class,
            RegisterGroup.class
    })
    public interface Group{}

    @NotNull(message = "用户ID不能为空", groups = {LoginGroup.class})
    private String userId;

    // 需要验证 属于默认组
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "邮箱不能为空", groups = {RegisterGroup.class})
    private String email;
}
