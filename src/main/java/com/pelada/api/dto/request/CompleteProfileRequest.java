package com.pelada.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CompleteProfileRequest(

        @NotBlank
        @Size(min = 3, max = 100)
        @NotBlank
        String fullName,

        @NotBlank
        @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$", message = "Invalid phone number")
        String phone,

        @NotBlank
        @Size(min = 2, max = 100)
        String city,

        @NotBlank
        @Size(min = 2, max = 2)
        String state

) { }