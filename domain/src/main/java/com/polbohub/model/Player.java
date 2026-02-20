package com.polbohub.model;

import java.util.UUID;

public record Player(
        UUID id,
        String licenseNumber,
        UUID teamId,
        UUID userId
) {
}
