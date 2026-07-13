package com.example.backend.dto.request;

import static com.example.backend.common.constants.MessageConstant.*;

import com.example.backend.common.validations.CreateValidation;
import com.example.backend.common.validations.EditValidation;
import com.example.backend.common.validations.PasswordMatches;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@PasswordMatches(
    groups={CreateValidation.class, EditValidation.class},
    message = PASSWORDS_DO_NOT_MATCH
)
public record UserRequest(
    @NotBlank(
        message = FIRST_NAME_REQUIRED, 
        groups={CreateValidation.class, EditValidation.class})
    @Size(
        max = 12, 
        message = FIRST_NAME_MAX_LENGTH, 
        groups={CreateValidation.class, EditValidation.class})
    String firstName,

    @NotBlank(
        message = FAMILY_NAME_REQUIRED, 
        groups={CreateValidation.class, EditValidation.class})
    @Size(
        max = 12, 
        message = FAMILY_NAME_MAX_LENGTH, 
        groups={CreateValidation.class, EditValidation.class})
    String familyName,

    @NotBlank(
        message = EMAIL_REQUIRED, 
        groups={CreateValidation.class, EditValidation.class})
    @Email(
        message = EMAIL_INVALID, 
        groups={CreateValidation.class, EditValidation.class})
    @Size(
        max = 50, 
        message = FAMILY_NAME_MAX_LENGTH, 
        groups={CreateValidation.class, EditValidation.class})
    String email,

    @NotBlank(
        message = PASSWORD_REQUIRED, 
        groups={CreateValidation.class})
    @Size(
        max = 16, 
        message = PASSWORD_MAX_LENGTH, 
        groups={CreateValidation.class, EditValidation.class})
    @Size(
        min = 8, 
        message = PASSWORD_MIN_LENGTH, 
        groups={CreateValidation.class, EditValidation.class})
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$", 
        message = PASSWORD_ALPHANUMERIC, 
        groups={CreateValidation.class, EditValidation.class})
    String password,

    @NotBlank(
        message = CONFIRM_PASSWORD_REQUIRED, 
        groups={CreateValidation.class})
    @Size(
        max = 16, 
        message = CONFIRM_PASSWORD_MAX_LENGTH, 
        groups={CreateValidation.class, EditValidation.class})
    @Size(
        min = 8, 
        message = PASSWORD_MIN_LENGTH, 
        groups={CreateValidation.class, EditValidation.class})
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$", 
        message = PASSWORD_ALPHANUMERIC, 
        groups={CreateValidation.class, EditValidation.class})
    String confirmPassword,

    @NotBlank(
        message = ROLE_REQUIRED, 
        groups={CreateValidation.class, EditValidation.class})
    String role
) {
}
