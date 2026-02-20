package com.polbohub.persistence.repository;

import com.polbohub.persistence.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<PlayerEntity, UUID> {
    List<PlayerEntity> findByTeam(UUID teamId);
    List<PlayerEntity> findByUser(UUID userId);
}
