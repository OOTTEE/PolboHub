package com.polbohub.domain.player;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record PlayerId (
        UUID value
) implements Serializable {
    public PlayerId {
        Objects.requireNonNull(value, "PlayerId cannot be null");
    }
}
