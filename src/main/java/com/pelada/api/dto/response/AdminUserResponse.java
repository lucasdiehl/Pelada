package com.pelada.api.dto.response;

import com.pelada.api.enums.Role;

import java.time.LocalDateTime;

public record AdminUserResponse(
        Long id,
        String fullName,
        String email,
        Role role,
        String city,
        String state,
        LocalDateTime createdAt
) { }