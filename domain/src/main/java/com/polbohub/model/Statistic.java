package com.polbohub.model;

import java.util.UUID;

public record Statistic(
        UUID id,
        String name,
        String description,
        Sport sport,
        String type
) {
}
