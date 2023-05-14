package org.hzz.i18n;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class User {

//    @NotNull(message = "{user.id.notnull}")
    private String userId;
    @NotNull
    // 未指定,使用hibernate validator提供的默认的国际化
    // "{javax.validation.constraints.NotNull.message}"
    private String userName;
}
