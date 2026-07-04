package com.example.backend.dto.response;

import lombok.Builder;

@Builder
public record SuccessResponse(
   String message 
) { 
}
