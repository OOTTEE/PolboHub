package com.polbohub.domain.player;

import com.polbohub.domain.team.TeamId;
import com.polbohub.domain.user.UserId;

public record Player(
        PlayerId id,
        String licenseNumber,
        TeamId teamId,
        UserId userId
) {
}
