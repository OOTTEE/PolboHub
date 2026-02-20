package com.polbohub.model;

import java.time.LocalDate;
import java.util.UUID;

public record StatisticValue(
        UUID id,
        UUID staticId,
        String value,
        LocalDate date
) {
}
