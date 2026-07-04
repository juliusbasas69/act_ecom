package com.example.backend.dto.request;

import static com.example.backend.common.constants.MessageConstant.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
    @NotBlank(message = FIRST_NAME_REQUIRED)
    @Size(max = 12, message = FIRST_NAME_MAX_LENGTH)
    String firstName,

    @NotBlank(message = FAMILY_NAME_REQUIRED)
    @Size(max = 12, message = FAMILY_NAME_MAX_LENGTH)
    String familyName,

    @NotBlank(message = EMAIL_REQUIRED)
    @Email(message = EMAIL_INVALID)
    @Size(max = 50, message = FAMILY_NAME_MAX_LENGTH)
    String email,

    @NotBlank(message = PASSWORD_REQUIRED)
    @Size(max = 16, message = PASSWORD_MAX_LENGTH)
    @Size(min = 8, message = PASSWORD_MIN_LENGTH)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$", message = PASSWORD_ALPHANUMERIC)
    String password,

    @NotBlank(message = CONFIRM_PASSWORD_REQUIRED)
    @Size(max = 16, message = CONFIRM_PASSWORD_MAX_LENGTH)
    @Size(min = 8, message = PASSWORD_MIN_LENGTH)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$", message = PASSWORD_ALPHANUMERIC)
    String confirmPassword,

    @NotBlank(message = ROLE_REQUIRED)
    String role
) {
}
