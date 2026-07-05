package com.pelada.api.dto.response;

import com.pelada.api.enums.NotificationType;

import java.time.LocalDateTime;

public record NotificationResponse(
        Long id,
        NotificationType type,
        String message,
        Boolean isRead,
        LocalDateTime createdAt,
        LocalDateTime readAt
) { }