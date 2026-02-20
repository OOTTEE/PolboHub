package com.polbohub.persistence.repository;

import com.polbohub.persistence.entity.StatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StatisticRepository extends JpaRepository<StatisticEntity, UUID> {
    Optional<StatisticEntity> findByName(String name);
    boolean existsByName(String name);
}
