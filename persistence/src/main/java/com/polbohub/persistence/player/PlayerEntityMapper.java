package com.polbohub.persistence.player;

import com.polbohub.domain.player.Player;

public class PlayerEntityMapper {

    public static PlayerEntity toEntity(Player player) {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(player.id());
        playerEntity.setLicenseNumber(player.licenseNumber());
        playerEntity.setTeam(player.teamId());
        playerEntity.setUser(player.userId());
        return playerEntity;
    }

    public static Player toDomain(PlayerEntity entity) {
        return new Player(entity.getId(), entity.getLicenseNumber(), entity.getTeam(), entity.getUser());
    }

}
