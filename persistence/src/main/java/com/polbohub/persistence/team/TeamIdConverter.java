package com.polbohub.persistence.team;

import com.polbohub.domain.team.TeamId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

@Converter(autoApply = true)
public class TeamIdConverter implements AttributeConverter<TeamId, UUID> {
    @Override
    public UUID convertToDatabaseColumn(TeamId teamId) {
        return teamId == null ? null : teamId.value();
    }

    @Override
    public TeamId convertToEntityAttribute(UUID uuid) {
        return uuid == null ? null : new TeamId(uuid);
    }
}
