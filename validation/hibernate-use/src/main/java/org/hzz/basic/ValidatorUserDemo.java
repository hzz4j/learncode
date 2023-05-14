package org.hzz.basic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidatorUserDemo {

    // 验证器
    private Validator validator;
    // 待验证的对象
    private User user;
    // 验证结果
    private Set<ConstraintViolation<User>> result;


    @BeforeEach
    public void init(){
        System.out.println("init");
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        user = new User();
    }

    @Test
    public void test(){
        // 验证
       result = validator.validate(user);
    }

    @AfterEach
    public void print(){
        result.forEach(System.out::println);
        System.out.println("-------------------------");
        result.forEach(r->{
            System.out.println(r.getMessage());
        });
    }
}
/**
 * ConstraintViolationImpl{interpolatedMessage='用户ID不能为空', propertyPath=userId, rootBeanClass=class org.hzz.basic.User, messageTemplate='用户ID不能为空'}
 * ConstraintViolationImpl{interpolatedMessage='不能为null', propertyPath=userName, rootBeanClass=class org.hzz.basic.User, messageTemplate='{javax.validation.constraints.NotNull.message}'}
 * -------------------------
 * 用户ID不能为空
 * 不能为null
 */