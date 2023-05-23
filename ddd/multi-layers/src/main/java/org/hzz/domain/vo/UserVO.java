package org.hzz.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserVO {

    @Schema(description = "用户id",example = "1220708537638920191")
    private Long id;

    @Schema(description = "用户名",example = "q10viking")
    private String name;

    @Schema(description = "密码",example = "**********")
    private String password;
}
