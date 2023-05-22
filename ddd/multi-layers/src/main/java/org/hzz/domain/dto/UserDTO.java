package org.hzz.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    @Schema(description = "用户id",example = "1220708537638920191")
    private Long id;

    @Schema(description = "用户名",example = "Q10Viking")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "用户密码",example = "123456")
    private String password;

    @Schema(description = "用户年龄",example = "1193094618@qq.com")
    @NotNull(message = "邮箱不能为空")
    private String email;

    @Schema(description = "创建时间",example = "2020-01-23 12:23:34")
    private Date created;
}
