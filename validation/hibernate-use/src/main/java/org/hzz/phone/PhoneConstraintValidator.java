package org.hzz.phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.regex.Pattern;

public class PhoneConstraintValidator implements ConstraintValidator<MyPhone,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 空值处理
        String phoneValue = Optional.ofNullable(value).orElse("");
        Pattern pattern = Pattern.compile("178\\d{8}");
        System.out.println("phoneValue = " + phoneValue);
        return pattern.matcher(phoneValue).matches();
    }
}
