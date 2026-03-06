package com.polbohub.persistence.player;

import com.polbohub.domain.player.PlayerId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

@Converter(autoApply = true)
public class PlayerIdConverter implements AttributeConverter<PlayerId, UUID> {
    @Override
    public UUID convertToDatabaseColumn(PlayerId playerId) {
        return playerId == null ? null : playerId.value();
    }

    @Override
    public PlayerId convertToEntityAttribute(UUID uuid) {
        return uuid == null ? null : new PlayerId(uuid);
    }
}
