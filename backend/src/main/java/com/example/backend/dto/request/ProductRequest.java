package com.example.backend.dto.request;

import static com.example.backend.common.constants.MessageConstant.*;

import java.math.BigDecimal;

import com.example.backend.common.validations.CreateValidation;
import com.example.backend.common.validations.EditValidation;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductRequest(

    @NotBlank(
        message = PRODUCT_CODE_REQUIRED,
        groups={CreateValidation.class, EditValidation.class})
    @Size(
        max = 50, 
        message = PRODUCT_CODE_MAX_LENGTH,
        groups={CreateValidation.class, EditValidation.class})
    String productCode,

    @NotBlank(
        message = PRODUCT_NAME_REQUIRED,
        groups={CreateValidation.class, EditValidation.class})
    @Size(
        max = 150, 
        message = PRODUCT_NAME_MAX_LENGTH, 
        groups={CreateValidation.class, EditValidation.class})
    String productName,

    @Size(
        max = 500, 
        message = "Description must not exceed 500 characters",
        groups={CreateValidation.class, EditValidation.class})
    String description,

    @NotNull(
        message = PRODUCT_PRICE_REQUIRED,
        groups={CreateValidation.class, EditValidation.class})
    @DecimalMin(
        value = "0.01", 
        message = PRODUCT_PRICE_INVALID,
        groups={CreateValidation.class, EditValidation.class})
    BigDecimal price,

    @NotNull(
        message = PRODUCT_STOCK_QUANTITY_REQUIRED, 
        groups={CreateValidation.class, EditValidation.class})
    @PositiveOrZero(
        message = PRODUCT_STOCK_QUANTITY_INVALID,
        groups={CreateValidation.class, EditValidation.class})
    Integer quantity,

    @NotBlank(
        message = PRODUCT_CATEGORY_REQUIRED, 
        groups={CreateValidation.class, EditValidation.class})
    String category,

    @NotBlank(
        message = PRODUCT_STATUS_REQUIRED, 
        groups={CreateValidation.class, EditValidation.class})
    String status

) {
}