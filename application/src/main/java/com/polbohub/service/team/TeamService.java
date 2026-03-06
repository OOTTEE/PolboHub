package com.polbohub.service.team;

import com.polbohub.domain.team.Team;
import com.polbohub.domain.team.TeamId;
import com.polbohub.domain.team.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

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
        return repository.save(dto);
    }

    public Team get(TeamId id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Team not found"));
    }

    public List<Team> list() {
        return repository.findAll();
    }

    public Team update(TeamId id, Team newTeamData) {
        return repository.findById(id)
                .map(current -> {
                    if (!current.name().equals(newTeamData.name()) && repository.existsByName(newTeamData.name())) {
                        throw new IllegalArgumentException("Team name already exists");
                    }
                    return current;
                })
                .map(_ -> new Team(id, newTeamData.name(), newTeamData.sport()))
                .map(repository::save)
                .orElseThrow(() -> new NoSuchElementException("Team not found"));
    }

    public void delete(TeamId id) {
        repository.deleteById(id);
    }

}
