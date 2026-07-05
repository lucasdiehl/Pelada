package com.pelada.api.dto.filter;

import com.pelada.api.enums.Role;

public record UserFilter (
        String search,
        Role role
) { }