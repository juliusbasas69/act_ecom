package com.example.backend.dto.response;

import java.sql.Timestamp;

import lombok.Builder;

@Builder
public record CategoryResponse(
    String encryptedId,
    String categoryCode,
    String categoryName,
    String description,
    String status,
    String color,
    Timestamp createdAt,
    Timestamp updatedAt
) {
}
