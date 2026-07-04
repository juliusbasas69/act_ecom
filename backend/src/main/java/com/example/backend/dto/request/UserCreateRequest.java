package com.example.backend.dto.request;

public record UserCreateRequest(
    String firstName,
    String familyName,
    String email,
    String password,
    String confirmPassword,
    String role
) {
}
