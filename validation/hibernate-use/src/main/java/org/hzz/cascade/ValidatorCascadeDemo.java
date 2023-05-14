package org.hzz.cascade;

import org.hzz.i18n.MyMessageInterpolator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Set;

public class ValidatorCascadeDemo {
    // 验证器
    private Validator validator;
    // 待验证的对象
    private User user;
    // 验证结果
    private Set<ConstraintViolation<User>> result;

    @BeforeEach
    public void init(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        user = new User();
//        user.setAddress(new Address());
        ArrayList<Address> addresses = new ArrayList<>();
        addresses.add(new Address());
        user.setAddressList(addresses);
    }

    @AfterEach
    public void print(){
        result.forEach(r->{
            System.out.println(r.getMessage());
        });
    }


    @Test
    public void validate(){
        result = validator.validate(user);
    }
}
