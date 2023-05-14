package org.hzz.group;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidateGroup {
    // 验证器
    private Validator validator;
    // 待验证的对象
    private User user;
    // 验证结果
    private Set<ConstraintViolation<User>> result;

    @BeforeEach
    public void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        user = new User();
    }

    @AfterEach
    public void print() {
        result.forEach(r -> {
            System.out.println(r.getMessage());
        });
    }

    @Test
    public void validate() {
        /**
         * 邮箱不能为空
         * 用户名不能为空
         */
        //result = validator.validate(user, User.RegisterGroup.class);

        /**
         * 用户ID不能为空
         * 用户名不能为空
         */
        result = validator.validate(user, User.LoginGroup.class);
    }
}
