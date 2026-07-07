package com.example.backend.dto.response;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Builder;

@Builder
public record ProductResponse(
    String encryptedId,
    String productName,
    BigDecimal price,
    Integer quantity,
    String description,
    Boolean isActive,
    Timestamp createdAt,
    Timestamp updatedAt
) {
}
