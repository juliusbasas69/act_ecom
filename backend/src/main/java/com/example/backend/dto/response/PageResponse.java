package com.example.backend.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record PageResponse<T>(

    List<T> content,

    PaginationResponse pagination
) {  
}
