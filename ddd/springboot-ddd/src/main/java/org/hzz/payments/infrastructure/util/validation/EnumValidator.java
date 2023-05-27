package org.hzz.payments.infrastructure.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
    private ValidEnum annotation;
    @Override
    public void initialize(ValidEnum annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        final boolean[] result = {false};
        Optional<Enum<?>[]> enumConstants = Optional.ofNullable(annotation.conformsTo().getEnumConstants());
        enumConstants.ifPresent(values ->{
            for (Enum<?> enumValue : values) {
                if (enumValue.name().equals(value)) {
                    result[0] = true;
                }
            }
        });
        return result[0];
    }
}
