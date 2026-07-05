package com.pelada.api.dto.response;

import com.pelada.api.enums.Role;
import com.pelada.api.enums.UserStatus;

public record UserResponse(
        Long id,
        String email,
        Role role,
        UserStatus status
) { }