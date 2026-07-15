package com.example.backend.dto.response;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Builder;

@Builder
public record ProductResponse(
    String encryptedId,
    Boolean isFeatured,
    String productCode,
    String productName,
    BigDecimal price,
    Integer quantity,
    String description,
    Boolean isActive,
    String category,
    String color,
    String status,
    String imageName,
    Timestamp createdAt,
    Timestamp updatedAt
) {
}
