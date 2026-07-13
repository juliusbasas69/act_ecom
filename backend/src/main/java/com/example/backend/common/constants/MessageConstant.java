package com.example.backend.common.constants;

public class MessageConstant {
    	
	public static final String INVALID_CREDENTIALS = "Invalid email or password";

    public static final String UNEXPECTED_ERROR_MESSAGE = "An unexpected error occurred.";


    //User
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_CREATED_MESSAGE = "User created successfully.";
    public static final String USER_UPDATED_MESSAGE = "User updated successfully.";
    public static final String USER_DELETED_MESSAGE = "User deleted successfully.";
    public static final String FIRST_NAME_REQUIRED = "First name is required.";
    public static final String FIRST_NAME_MAX_LENGTH = "First name must not exceed 12 characters.";
    public static final String FAMILY_NAME_REQUIRED = "Family name is required.";
    public static final String FAMILY_NAME_MAX_LENGTH = "Family name must not exceed 12 characters.";
    public static final String EMAIL_REQUIRED = "Email is required.";
    public static final String EMAIL_INVALID = "Please enter a valid email address.";
    public static final String EMAIL_MAX_LENGTH = "Email Address must not exceed 50 characters.";
    public static final String PASSWORD_REQUIRED = "Password is required.";
    public static final String PASSWORDS_DO_NOT_MATCH = "Password and confirm password do not match.";
    public static final String PASSWORD_MAX_LENGTH = "Password must not exceed 16 characters.";
    public static final String PASSWORD_MIN_LENGTH = "Password must be at least 8 characters.";
    public static final String PASSWORD_ALPHANUMERIC = "Password must contain only letters and numbers.";
    public static final String CONFIRM_PASSWORD_REQUIRED = "Confirm password is required.";
    public static final String CONFIRM_PASSWORD_MAX_LENGTH = "Confirm password must not exceed 16 characters.";
    public static final String ROLE_REQUIRED = "Role is required.";

    //Product
    public static final String PRODUCT_NOT_FOUND = "Product not found.";
    public static final String PRODUCT_CREATED_MESSAGE = "Product created successfully.";
    public static final String PRODUCT_UPDATED_MESSAGE = "Product updated successfully.";
    public static final String PRODUCT_DELETED_MESSAGE = "Product deleted successfully.";
    public static final String PRODUCT_CODE_REQUIRED = "Product code is required.";
    public static final String PRODUCT_CODE_MAX_LENGTH = "Product code must not exceed 50 characters.";
    public static final String PRODUCT_NAME_REQUIRED = "Product name is required.";
    public static final String PRODUCT_NAME_MAX_LENGTH = "Product name must not exceed 150 characters.";
    public static final String PRODUCT_DESCRIPTION_MAX_LENGTH = "Description must not exceed 500 characters.";
    public static final String PRODUCT_PRICE_REQUIRED = "Price is required.";
    public static final String PRODUCT_PRICE_INVALID = "Price must be greater than 0.";
    public static final String PRODUCT_STOCK_QUANTITY_REQUIRED = "Stock quantity is required.";
    public static final String PRODUCT_STOCK_QUANTITY_INVALID = "Stock quantity cannot be negative.";
    public static final String PRODUCT_CATEGORY_REQUIRED = "Category is required.";
    public static final String PRODUCT_STATUS_REQUIRED = "Status is required.";

    // Category
    public static final String CATEGORY_NOT_FOUND = "Category not found.";
    public static final String CATEGORY_CREATED_MESSAGE = "Category created successfully.";
    public static final String CATEGORY_UPDATED_MESSAGE = "Category updated successfully.";
    public static final String CATEGORY_DELETED_MESSAGE = "Category deleted successfully.";
    public static final String CATEGORY_CODE_REQUIRED = "Category code is required.";
    public static final String CATEGORY_CODE_MAX_LENGTH = "Category code must not exceed 6 characters.";
    public static final String CATEGORY_NAME_REQUIRED = "Category name is required.";
    public static final String CATEGORY_NAME_MAX_LENGTH = "Category name must not exceed 32 characters.";
    public static final String CATEGORY_STATUS_REQUIRED = "Status is required.";
    public static final String CATEGORY_CODE_ALREADY_EXISTS = "Category code already exists.";
    public static final String CATEGORY_NAME_ALREADY_EXISTS = "Category name already exists.";
    public static final String CATEGORY_STATUS_INVALID = "Status must be either ACTIVE or INACTIVE.";
}
