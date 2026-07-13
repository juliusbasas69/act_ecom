package com.example.backend.dto.response;

import java.sql.Timestamp;

import lombok.Builder;

@Builder
public record CategoryResponse(
    String encryptedId,
    String categoryCode,
    String categoryName,
    String status,
    Timestamp createdAt,
    Timestamp updatedAt
) {
}
