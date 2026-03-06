package com.polbohub.service.player;

import com.polbohub.domain.player.Player;
import com.polbohub.domain.player.PlayerId;
import com.polbohub.domain.player.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class PlayerService {

    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public Optional<Player> create(Player player) {
        return repository.save(player);
    }

    public Player get(PlayerId playerId) {
        return repository.findById(playerId)
                .orElseThrow(() -> new NoSuchElementException("Player not found"));
    }

    public List<Player> list() {
        return repository.findAll();
    }

    public Optional<Player> update(PlayerId playerId, Player player) {
        return repository.findById(playerId)
                .map(storedPlayer -> new Player(
                        storedPlayer.id(),
                        player.licenseNumber(),
                        player.teamId(),
                        player.userId()
                ))
                .flatMap(repository::save);
    }

    public void delete(PlayerId playerId) {
        repository.deleteById(playerId);
    }

}
