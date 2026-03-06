package com.polbohub.persistence.player;

import com.polbohub.domain.player.PlayerId;
import com.polbohub.domain.team.TeamId;
import com.polbohub.domain.user.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaPlayerRepository extends JpaRepository<PlayerEntity, PlayerId> {
    List<PlayerEntity> findByTeam(TeamId teamId);

    List<PlayerEntity> findByUser(UserId userId);
}
