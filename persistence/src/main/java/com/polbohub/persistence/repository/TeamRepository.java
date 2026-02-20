package com.polbohub.persistence.repository;

import com.polbohub.persistence.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<TeamEntity, UUID> {
    Optional<TeamEntity> findByName(String name);
    boolean existsByName(String name);
}
