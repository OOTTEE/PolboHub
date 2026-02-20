package com.polbohub.api.v1.statisticvalue;

import com.polbohub.service.statisticvalue.StatisticValueService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class StatisticValueController implements StatisticValueApi {

    private final StatisticValueService service;

    public StatisticValueController(StatisticValueService service) {
        this.service = service;
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public StatisticValueDto create(StatisticValueDto dto) {
        return toResponse(service.create(toDto(dto)));
    }

    @Override
    public StatisticValueDto get(UUID id) {
        return toResponse(service.get(id));
    }

    @Override
    public List<StatisticValueDto> list() {
        return service.list().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public StatisticValueDto update(UUID id, StatisticValueDto dto) {
        return toResponse(service.update(id, toDto(dto)));
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(UUID id) {
        service.delete(id);
    }

    private com.polbohub.model.StatisticValue toDto(StatisticValueDto request) {
        return new com.polbohub.model.StatisticValue(
                request.getId(),
                request.getStaticId(),
                request.getValue(),
                request.getDate()
        );
    }

    private StatisticValueDto toResponse(com.polbohub.model.StatisticValue dto) {
        StatisticValueDto response = new StatisticValueDto();
        response.setId(dto.id());
        response.setStaticId(dto.staticId());
        response.setValue(dto.value());
        response.setDate(dto.date());
        return response;
    }
}
