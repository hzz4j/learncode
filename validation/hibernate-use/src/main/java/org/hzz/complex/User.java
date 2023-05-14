package org.hzz.complex;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class User {
    @NotNull(message = "用户ID不能为空")
    private String userId;
    @NotBlank(message = "用户名不能为空")
    private String userName;
}
