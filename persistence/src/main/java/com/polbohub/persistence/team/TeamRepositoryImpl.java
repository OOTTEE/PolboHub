package com.polbohub.persistence.team;

import com.polbohub.domain.team.Team;
import com.polbohub.domain.team.TeamId;
import com.polbohub.domain.team.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class TeamRepositoryImpl implements TeamRepository {
    private final JpaTeamRepository repository;


    public TeamRepositoryImpl(JpaTeamRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Team> findById(TeamId teamId) {
        return repository.findById(teamId).map(TeamEntityMapper::toDomain);
    }

    @Override
    public Team save(Team team) {
        return TeamEntityMapper.toDomain(repository.save(TeamEntityMapper.toEntity(team)));
    }

    @Override
    public void deleteById(TeamId id) {
        repository.deleteById(id);
    }

    @Override
    public List<Team> findAll() {
        return repository.findAll().stream().map(TeamEntityMapper::toDomain).toList();
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }
}
