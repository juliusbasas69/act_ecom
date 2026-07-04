package com.example.backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
public record PaginationResponse(
    int page,
    int totalPages,
    long totalElements,
    boolean hasNext,
    boolean hasPrevious
) {
}
