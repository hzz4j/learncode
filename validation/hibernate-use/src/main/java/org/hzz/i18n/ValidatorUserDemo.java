package org.hzz.i18n;

import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Locale;
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
        // 设置默认的locale
        // 因为Hibernator的ResourceBundleMessageInterpolator默认使用的是Locale.getDefault()
//        Locale.setDefault(Locale.JAPAN);
//        Locale.setDefault(Locale.ENGLISH);
        Locale.setDefault(Locale.CHINESE);

        validator = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new MyMessageInterpolator())
                .buildValidatorFactory()
                .getValidator();

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
 * ConstraintViolationImpl{interpolatedMessage='不得为 null', propertyPath=userName, rootBeanClass=class org.hzz.i18n.User, messageTemplate='{javax.validation.constraints.NotNull.message}'}
 * ConstraintViolationImpl{interpolatedMessage='userID不能为null', propertyPath=userId, rootBeanClass=class org.hzz.i18n.User, messageTemplate='{user.id.notnull}'}
 * -------------------------
 * 不得为 null
 * userID不能为null
 */