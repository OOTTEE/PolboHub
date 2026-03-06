package com.polbohub.domain.team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository {

    List<Team> findAll();

    Optional<Team> findById(TeamId id);

    Team save(Team team);

    void deleteById(TeamId id);

    boolean existsByName(String name);
}
