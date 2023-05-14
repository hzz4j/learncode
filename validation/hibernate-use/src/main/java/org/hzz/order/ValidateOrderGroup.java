package org.hzz.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidateOrderGroup {
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
        user.setUserName("hzz");
    }

    @AfterEach
    public void print() {
        result.forEach(r -> {
            System.out.println(r.getMessage());
        });
    }

    @Test
    public void validate() {
        result = validator.validate(user, User.Group.class);
    }
}
