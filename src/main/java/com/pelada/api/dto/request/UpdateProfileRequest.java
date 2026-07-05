package com.pelada.api.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(

        @Size(min = 3, max = 100)
        String fullName,

        @Size(min = 10, max = 15)
        String phone,

        @Size(min = 2, max = 100)
        String city,

        @Size(min = 2, max = 2)
        String state,

        String photoUrl

) { }