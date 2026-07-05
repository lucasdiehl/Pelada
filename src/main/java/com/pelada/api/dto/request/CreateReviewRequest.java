package com.pelada.api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateReviewRequest(

        @NotNull
        Long targetUserId,

        @NotNull
        @Min(1)
        @Max(5)
        Byte rating,

        @Size(max = 500)
        String comment

) { }