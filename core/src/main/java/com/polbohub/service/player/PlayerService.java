package com.polbohub.service.player;

import com.polbohub.model.Player;
import com.polbohub.persistence.entity.PlayerEntity;
import com.polbohub.persistence.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlayerService {

    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public Player create(Player dto) {
        PlayerEntity entity = toEntity(dto);
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        PlayerEntity saved = repository.save(entity);
        return toDto(saved);
    }

    public Player get(UUID id) {
        return repository.findById(id).map(this::toDto)
                .orElseThrow(() -> new NoSuchElementException("Player not found"));
    }

    public List<Player> list() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Player update(UUID id, Player dto) {
        PlayerEntity entity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Player not found"));
        entity.setTeam(dto.teamId());
        entity.setUser(dto.userId());
        return toDto(repository.save(entity));
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private PlayerEntity toEntity(Player dto) {
        PlayerEntity e = new PlayerEntity();
        e.setId(dto.id());
        e.setTeam(dto.teamId());
        e.setLicenseNumber(dto.licenseNumber());
        e.setUser(dto.userId());
        return e;
    }

    private Player toDto(PlayerEntity e) {
        return new Player(
                e.getId(),
                e.getLicenseNumber(),
                e.getTeam(),
                e.getUser()
        );
    }
}
