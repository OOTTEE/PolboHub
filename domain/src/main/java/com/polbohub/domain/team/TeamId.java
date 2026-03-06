package com.polbohub.domain.team;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record TeamId(
        UUID value
) implements Serializable {
    public TeamId {
        Objects.requireNonNull(value, "TeamId cannot be null");
    }
}
