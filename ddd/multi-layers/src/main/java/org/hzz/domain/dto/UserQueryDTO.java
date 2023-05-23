package org.hzz.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserQueryDTO {
    @Schema(description = "用户名")
    private String name;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "邮箱")
    @Email
    private String email;
}
