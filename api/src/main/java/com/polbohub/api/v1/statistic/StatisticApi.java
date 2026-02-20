package com.polbohub.api.v1.statistic;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/statistics")
public interface StatisticApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StatisticDto create(@Valid @RequestBody StatisticDto dto);

    @GetMapping("/{id}")
    public StatisticDto get(@PathVariable UUID id);

    @GetMapping
    public List<StatisticDto> list();

    @PutMapping("/{id}")
    public StatisticDto update(@PathVariable UUID id, @Valid @RequestBody StatisticDto dto);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id);
}
