package com.pelada.api.dto.response;

import java.time.DayOfWeek;
import java.util.Set;

public record AvailabilityResponse(
        Set<DayOfWeek> days
) { }
