package com.polbohub.api.v1.player;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/players")
public interface PlayerApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerDto create(@Valid @RequestBody PlayerDto dto);

    @GetMapping("/{id}")
    public PlayerDto get(@PathVariable UUID id);

    @GetMapping
    public List<PlayerDto> list();

    @PutMapping("/{id}")
    public PlayerDto update(@PathVariable UUID id, @Valid @RequestBody PlayerDto dto);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id);
}
