package com.polbohub.domain.player;

import com.polbohub.domain.team.TeamId;
import com.polbohub.domain.user.UserId;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {

    Optional<Player> save(Player player);
    Optional<Player> findById(PlayerId id);
    List<Player> findAll();
    void deleteById(PlayerId id);

    List<Player> findByTeam(TeamId teamId);
    List<Player> findByUser(UserId userId);

}
