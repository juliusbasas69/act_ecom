package com.example.backend.dao.projection;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record ProductData(

    Integer id,

    String productCode,

    String productName,

    BigDecimal price,

    Integer quantity,

    String description,

    String category,

    String color,

    String status,

    Boolean isFeatured,

    Timestamp createdAt,

    Timestamp updatedAt,

    Boolean isDeleted

) {
}
