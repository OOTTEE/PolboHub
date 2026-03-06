package com.polbohub.domain.user;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record UserId(
        UUID value
) implements Serializable {
    public UserId {
        Objects.requireNonNull(value, "UserId cannot be null");
    }
}
