package com.polbohub.api.v1.statistic;

import com.polbohub.model.Sport;
import com.polbohub.service.statistic.StatisticService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class StatisticController implements StatisticApi {

    private final StatisticService service;

    public StatisticController(StatisticService service) {
        this.service = service;
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public StatisticDto create(StatisticDto dto) {
        return toResponse(service.create(toDto(dto)));
    }

    @Override
    public StatisticDto get(UUID id) {
        return toResponse(service.get(id));
    }

    @Override
    public List<StatisticDto> list() {
        return service.list().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public StatisticDto update(UUID id, StatisticDto dto) {
        return toResponse(service.update(id, toDto(dto)));
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(UUID id) {
        service.delete(id);
    }

    private com.polbohub.model.Statistic toDto(StatisticDto request) {
        return new com.polbohub.model.Statistic(
                request.getId(),
                request.getName(),
                request.getDescription(),
                Sport.valueOf(request.getSport().name()),
                request.getType()
        );
    }

    private StatisticDto toResponse(com.polbohub.model.Statistic dto) {
        StatisticDto response = new StatisticDto();
        response.setId(dto.id());
        response.setName(dto.name());
        response.setDescription(dto.description());
        response.setSport(com.polbohub.api.v1.Sport.valueOf(dto.sport().name()));
        response.setType(dto.type());
        return response;
    }
}
