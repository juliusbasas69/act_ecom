package com.example.backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationResponse {
    
    private int page;

    private int totalPages;

    private long totalElements;

    private boolean hasNext;

    private boolean hasPrevious;

}
