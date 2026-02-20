package com.polbohub.api.v1.team;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/teams")
public interface TeamApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamDto create(@Valid @RequestBody TeamDto dto);

    @GetMapping("/{id}")
    public TeamDto get(@PathVariable UUID id);

    @GetMapping
    public List<TeamDto> list();

    @PutMapping("/{id}")
    public TeamDto update(@PathVariable UUID id, @Valid @RequestBody TeamDto dto);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id);
}
