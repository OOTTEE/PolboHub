package com.polbohub.api.v1.statisticvalue;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/statistic-values")
public interface StatisticValueApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StatisticValueDto create(@Valid @RequestBody StatisticValueDto dto);

    @GetMapping("/{id}")
    public StatisticValueDto get(@PathVariable UUID id);

    @GetMapping
    public List<StatisticValueDto> list();

    @PutMapping("/{id}")
    public StatisticValueDto update(@PathVariable UUID id, @Valid @RequestBody StatisticValueDto dto);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id);
}
