package com.example.backend.dto.response;

import java.sql.Timestamp;

import lombok.Builder;

@Builder
public record UserResponse(
    String encryptedId,
    String firstName,
    String familyName,
    String email,
    Timestamp createdAt,
    Timestamp updatedAt,
    String role
) {
}
