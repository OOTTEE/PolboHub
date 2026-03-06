package com.polbohub.persistence.team;

import com.polbohub.domain.team.TeamId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaTeamRepository extends JpaRepository<TeamEntity, TeamId> {
    Optional<TeamEntity> findByName(String name);
    boolean existsByName(String name);
}
