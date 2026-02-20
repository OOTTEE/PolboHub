package com.polbohub.service.statistic;

import com.polbohub.model.Statistic;
import com.polbohub.persistence.entity.Sport;
import com.polbohub.persistence.entity.StatisticEntity;
import com.polbohub.persistence.repository.StatisticRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class StatisticService {

    private final StatisticRepository repository;

    public StatisticService(StatisticRepository repository) {
        this.repository = repository;
    }

    public Statistic create(Statistic dto) {
        if (repository.existsByName(dto.name())) {
            throw new IllegalArgumentException("Statistic name already exists");
        }
        StatisticEntity entity = toEntity(dto);
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        StatisticEntity saved = repository.save(entity);
        return toDto(saved);
    }

    public Statistic get(UUID id) {
        return repository.findById(id).map(this::toDto)
                .orElseThrow(() -> new NoSuchElementException("Statistic not found"));
    }

    public List<Statistic> list() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Statistic update(UUID id, Statistic dto) {
        StatisticEntity entity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Statistic not found"));
        if (!entity.getName().equals(dto.name()) && repository.existsByName(dto.name())) {
            throw new IllegalArgumentException("Statistic name already exists");
        }
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setSport(Sport.valueOf(dto.sport().name()));
        entity.setType(dto.type());
        return toDto(repository.save(entity));
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private StatisticEntity toEntity(Statistic dto) {
        StatisticEntity e = new StatisticEntity();
        e.setId(dto.id());
        e.setName(dto.name());
        e.setDescription(dto.description());
        e.setSport(Sport.valueOf(dto.sport().name()));
        e.setType(dto.type());
        return e;
    }

    private Statistic toDto(StatisticEntity e) {
        return new Statistic(
                e.getId(),
                e.getName(),
                e.getDescription(),
                com.polbohub.model.Sport.valueOf(e.getSport().name()),
                e.getType()
        );
    }
}
