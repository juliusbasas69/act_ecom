package com.example.backend.dto.request;

import static com.example.backend.common.constants.MessageConstant.*;

import com.example.backend.common.validations.CreateValidation;
import com.example.backend.common.validations.EditValidation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CategoryRequest(

    @NotBlank(
        message = CATEGORY_CODE_REQUIRED,
        groups = {CreateValidation.class, EditValidation.class})
    @Size(
        max = 32, 
        message = CATEGORY_CODE_MAX_LENGTH,
        groups = {CreateValidation.class, EditValidation.class})
    String categoryCode,

    @NotBlank(
        message = CATEGORY_NAME_REQUIRED,
        groups = {CreateValidation.class, EditValidation.class})
    @Size(
        max = 32, 
        message = CATEGORY_NAME_MAX_LENGTH,
        groups = {CreateValidation.class, EditValidation.class})
    String categoryName,

    @NotBlank(
        message = CATEGORY_DESCRIPTION_REQUIRED,
        groups = {CreateValidation.class, EditValidation.class})
    @Size(
        max = 255, 
        message = CATEGORY_DESCRIPTION_MAX_LENGTH,
        groups = {CreateValidation.class, EditValidation.class})
    String description,

    @NotBlank(
        message = CATEGORY_STATUS_REQUIRED,
        groups = {CreateValidation.class, EditValidation.class})
    @Pattern(
        regexp = "ACTIVE|INACTIVE",
        message = CATEGORY_STATUS_INVALID
    )
    String status,

    @NotBlank(
        message = CATEGORY_COLOR_REQUIRED,
        groups = {CreateValidation.class, EditValidation.class})
    String color,

    @NotBlank(
        message = CATEGORY_ICON_REQUIRED,
        groups = {CreateValidation.class, EditValidation.class})
    String icon
) {   
} 
