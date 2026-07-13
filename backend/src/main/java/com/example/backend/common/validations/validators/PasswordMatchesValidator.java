package com.example.backend.common.validations.validators;

import java.util.Objects;

import com.example.backend.common.constants.MessageConstant;
import com.example.backend.common.validations.PasswordMatches;
import com.example.backend.dto.request.UserRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, UserRequest> {

    @Override
    public boolean isValid(
            UserRequest request,
            ConstraintValidatorContext context) {

        if (request == null) {
            return true;
        }

        // Don't validate if both are null (use @NotBlank for create)
        if (request.password() == null && request.confirmPassword() == null) {
            return true;
        }

        if (!Objects.equals(request.password(), request.confirmPassword())) {

            context.disableDefaultConstraintViolation();

            context.buildConstraintViolationWithTemplate(
                    MessageConstant.PASSWORDS_DO_NOT_MATCH)
                   .addPropertyNode("password")
                   .addConstraintViolation();

            return false;
        }

        return true;
    }
}