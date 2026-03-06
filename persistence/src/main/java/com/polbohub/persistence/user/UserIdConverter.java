package com.polbohub.persistence.user;

import com.polbohub.domain.user.UserId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

@Converter(autoApply = true)
public class UserIdConverter implements AttributeConverter<UserId, UUID> {
    @Override
    public UUID convertToDatabaseColumn(UserId userId) {
        return userId == null ? null : userId.value();
    }

    @Override
    public UserId convertToEntityAttribute(UUID uuid) {
        return uuid == null ? null : new UserId(uuid);
    }
}
