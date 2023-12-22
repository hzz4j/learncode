package org.hzz.ddd.interfaces.tracking;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for {@link TrackCommand}s.
 */
public class TrackCommandValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return  TrackCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "trackingId", "error.required", "Required");
    }
}
