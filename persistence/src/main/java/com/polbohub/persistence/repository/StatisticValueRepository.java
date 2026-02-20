package com.polbohub.persistence.repository;

import com.polbohub.persistence.entity.StatisticValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StatisticValueRepository extends JpaRepository<StatisticValueEntity, UUID> {
    List<StatisticValueEntity> findByStaticId(UUID staticId);
}
