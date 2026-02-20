package com.polbohub.api.v1.player;

import com.polbohub.service.player.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class PlayerController implements PlayerApi {

    private final PlayerService service;

    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerDto create(PlayerDto dto) {
        return toResponse(service.create(toDto(dto)));
    }

    @Override
    public PlayerDto get(UUID id) {
        return toResponse(service.get(id));
    }

    @Override
    public List<PlayerDto> list() {
        return service.list().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public PlayerDto update(UUID id, PlayerDto dto) {
        return toResponse(service.update(id, toDto(dto)));
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(UUID id) {
        service.delete(id);
    }

    private com.polbohub.model.Player toDto(PlayerDto request) {
        return new com.polbohub.model.Player(
                request.getId(),
                request.getLicenseNumber(),
                request.getTeamId(),
                request.getUserId()
        );
    }

    private PlayerDto toResponse(com.polbohub.model.Player dto) {
        PlayerDto response = new PlayerDto();
        response.setId(dto.id());
        response.setLicenseNumber(dto.licenseNumber());
        response.setTeamId(dto.teamId());
        response.setUserId(dto.userId());
        return response;
    }
}
