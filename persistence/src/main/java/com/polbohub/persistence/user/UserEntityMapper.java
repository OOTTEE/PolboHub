package com.polbohub.persistence.user;

import com.polbohub.domain.user.User;

public class UserEntityMapper {

    static public UserEntity toEntity(User dto) {
        UserEntity entity = new UserEntity();
        entity.setId(dto.id());
        entity.setUsername(dto.username());
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setBirthDate(dto.birthDate());
        return entity;
    }

    static public User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthDate()
        );
    }

}
