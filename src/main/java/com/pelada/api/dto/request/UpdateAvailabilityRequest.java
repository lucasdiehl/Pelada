package com.pelada.api.dto.request;

import java.time.DayOfWeek;
import java.util.Set;

public record UpdateAvailabilityRequest(

        Set<DayOfWeek> days

) { }