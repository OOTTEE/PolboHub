package com.polbohub.service.statisticvalue;

import com.polbohub.model.StatisticValue;
import com.polbohub.persistence.entity.StatisticValueEntity;
import com.polbohub.persistence.repository.StatisticValueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class StatisticValueService {

    private final StatisticValueRepository repository;

    public StatisticValueService(StatisticValueRepository repository) {
        this.repository = repository;
    }

    public StatisticValue create(StatisticValue dto) {
        StatisticValueEntity entity = toEntity(dto);
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        StatisticValueEntity saved = repository.save(entity);
        return toDto(saved);
    }

    public StatisticValue get(UUID id) {
        return repository.findById(id).map(this::toDto)
                .orElseThrow(() -> new NoSuchElementException("StatisticValue not found"));
    }

    public List<StatisticValue> list() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public StatisticValue update(UUID id, StatisticValue dto) {
        StatisticValueEntity entity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("StatisticValue not found"));
        entity.setStaticId(dto.staticId());
        entity.setValue(dto.value());
        return toDto(repository.save(entity));
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private StatisticValueEntity toEntity(StatisticValue dto) {
        StatisticValueEntity e = new StatisticValueEntity();
        e.setId(dto.id());
        e.setStaticId(dto.staticId());
        e.setValue(dto.value());
        e.setDate(dto.date());
        return e;
    }

    private StatisticValue toDto(StatisticValueEntity e) {
        return new StatisticValue(
                e.getId(),
                e.getStaticId(),
                e.getValue(),
                e.getDate()
        );
    }
}
