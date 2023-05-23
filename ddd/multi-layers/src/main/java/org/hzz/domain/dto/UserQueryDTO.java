package org.hzz.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UserQueryDTO {
    @Schema(description = "用户名",example = "Q10Viking")
    private String username;

    @Schema(description = "年龄",example = "18")
    private Integer age;

    @Schema(description = "邮箱",example = "1193094618@qq.com")
    @Email
    private String email;
}
