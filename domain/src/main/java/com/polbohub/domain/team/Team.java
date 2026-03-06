package com.polbohub.domain.team;

import com.polbohub.domain.Sport;

public record Team(
        TeamId id,
        String name,
        Sport sport
) {
}
