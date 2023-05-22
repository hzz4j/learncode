package org.hzz.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserVO {

    @Schema(description = "用户名",example = "q10viking",title = "用户名t")
    private String name;

    @Schema(description = "密码",example = "123456")
    private String password;
}
