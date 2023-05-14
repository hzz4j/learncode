package org.hzz.basic;

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
}
