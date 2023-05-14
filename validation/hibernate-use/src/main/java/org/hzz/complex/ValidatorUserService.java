package org.hzz.complex;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

public class ValidatorUserService {
    // 验证器
    private Validator validator;

    // 验证结果
    private Set<ConstraintViolation<UserService>> result;
    @BeforeEach
    public void init(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @AfterEach
    public void print(){
        result.forEach(r->{
            System.out.println(r.getMessage());
        });
    }


    @Test
    public void validateMethodParameters() throws NoSuchMethodException {
        UserService userService = new UserService();
        Method method = userService.getClass().getMethod("saveUser", User.class);
        // 验证
        result  = validator.forExecutables()
                .validateParameters(userService, method, new Object[]{new User()});
    }
    /**output
     * 用户ID不能为空
     * 用户名不能为空
     */


    @Test
    public void validateConstructorParameters() throws NoSuchMethodException {
        Constructor<UserService> method = UserService.class.getConstructor(User.class);
//        User user = new User();
//        user.setUserId("001");
//        user.setUserName("hzz");
        // 验证
        result  = validator.forExecutables()
                .validateConstructorParameters(method, new Object[]{new User()});
    }
    /**output
     * 用户ID不能为空
     * 用户名不能为空
     */


    @Test
    public void validateMethodReturnValue() throws NoSuchMethodException {
        UserService userService = new UserService();
        Method method = userService.getClass().getMethod("getUser");
        // 验证
        result  = validator.forExecutables()
                .validateReturnValue(userService, method, userService.getUser());
    }
    /**output
     * 用户ID不能为空
     */
}
