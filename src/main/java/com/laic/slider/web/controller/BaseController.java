package com.laic.slider.web.controller;

import com.laic.slider.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.Set;

@Slf4j
public class BaseController {
    @Autowired
    private Validator validator;

    public <T> void validate(T target) {
        validate(target, null);
    }

    public <T> void validate(T target, Errors errors) {
        Set<ConstraintViolation<T>> violations = validator.validate(target);
        if(violations.size() > 0) {
            String exceptionMessage = "";
            for(ConstraintViolation<T> violation : violations) {
                ConstraintDescriptor<?> test = violation.getConstraintDescriptor();
                String propertyPath = violation.getPropertyPath().toString();
                String message = violation.getMessage();
                if(errors != null) {
                    errors.rejectValue(propertyPath, "", message);
                }
                exceptionMessage = String.format("%s[%s]", message, propertyPath);
//                log.error("{}", exceptionMessage);
            }
            throw new InvalidRequestException(exceptionMessage);
        }
    }

}
