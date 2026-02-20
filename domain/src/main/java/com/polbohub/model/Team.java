package com.polbohub.model;

import java.util.UUID;

public record Team(
        UUID id,
        String name,
        Sport sport
) {
}
