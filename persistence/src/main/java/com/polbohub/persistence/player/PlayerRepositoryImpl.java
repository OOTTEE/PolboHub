package com.polbohub.persistence.player;

import com.polbohub.domain.player.Player;
import com.polbohub.domain.player.PlayerId;
import com.polbohub.domain.player.PlayerRepository;
import com.polbohub.domain.team.TeamId;
import com.polbohub.domain.user.UserId;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class PlayerRepositoryImpl implements PlayerRepository {

    private final JpaPlayerRepository playerRepository;

    public PlayerRepositoryImpl(JpaPlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Optional<Player> save(Player player) {
        return Optional.of(PlayerEntityMapper.toDomain(playerRepository.save(PlayerEntityMapper.toEntity(player))));
    }

    @Override
    public Optional<Player> findById(PlayerId playerId) {
        return playerRepository.findById(playerId).map(PlayerEntityMapper::toDomain);
    }

    @Override
    public List<Player> findAll() {
        return playerRepository.findAll().stream().map(PlayerEntityMapper::toDomain).toList();
    }

    @Override
    public void deleteById(PlayerId id) {
        playerRepository.deleteById(id);
    }

    @Override
    public List<Player> findByTeam(TeamId teamId) {
        return playerRepository.findByTeam(teamId).stream().map(PlayerEntityMapper::toDomain).toList();
    }

    @Override
    public List<Player> findByUser(UserId userId) {
        return playerRepository.findByUser(userId).stream().map(PlayerEntityMapper::toDomain).toList();
    }
}
