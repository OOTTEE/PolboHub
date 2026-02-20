package com.polbohub.service.team;

import com.polbohub.model.Team;
import com.polbohub.persistence.entity.Sport;
import com.polbohub.persistence.entity.TeamEntity;
import com.polbohub.persistence.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamService {

    private final TeamRepository repository;

    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public Team create(Team dto) {
        if (repository.existsByName(dto.name())) {
            throw new IllegalArgumentException("Team name already exists");
        }
        TeamEntity entity = toEntity(dto);
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        TeamEntity saved = repository.save(entity);
        return toDto(saved);
    }

    public Team get(UUID id) {
        return repository.findById(id).map(this::toDto)
                .orElseThrow(() -> new NoSuchElementException("Team not found"));
    }

    public List<Team> list() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Team update(UUID id, Team dto) {
        TeamEntity entity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Team not found"));
        if (!entity.getName().equals(dto.name()) && repository.existsByName(dto.name())) {
            throw new IllegalArgumentException("Team name already exists");
        }
        entity.setName(dto.name());
        entity.setSport(Sport.valueOf(dto.sport().name()));
        return toDto(repository.save(entity));
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private TeamEntity toEntity(Team dto) {
        TeamEntity e = new TeamEntity();
        e.setId(dto.id());
        e.setName(dto.name());
        e.setSport(Sport.valueOf(dto.sport().name()));
        return e;
    }

    private Team toDto(TeamEntity e) {
        return new Team(
                e.getId(),
                e.getName(),
                com.polbohub.model.Sport.valueOf(e.getSport().name())
        );
    }
}
