package com.pelada.api.dto.response;

import com.pelada.api.enums.Role;

public record ProfileResponse(
        Long id,
        Long userId,
        String fullName,
        String phone,
        String city,
        String state,
        String photoUrl,
        Role role
) { }