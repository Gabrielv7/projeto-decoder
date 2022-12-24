package com.ead.authuser.validator.impl;

import com.ead.authuser.validator.UsernameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameConstraintImpl implements ConstraintValidator<UsernameConstraint, String> {

    @Override
    public void initialize(UsernameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null || username.trim().isEmpty() || username.contains(" ") || username.length() < 4) {
            return false;
        }
        return true;
    }
}
