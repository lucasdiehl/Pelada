package com.pelada.api.dto.response;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        String authorName,
        Byte rating,
        String comment,
        LocalDateTime createdAt
) { }
