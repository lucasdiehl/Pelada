package com.pelada.api.dto.request;

import com.pelada.api.enums.Role;
import jakarta.validation.constraints.*;

public record RegisterRequest(

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 64, message = "Password must have at least 8 characters")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "Password must contain uppercase, lowercase and number")
        String password,

        @NotNull(message = "Role is required")
        Role role

) { }