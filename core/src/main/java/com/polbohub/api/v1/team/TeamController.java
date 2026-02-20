package com.polbohub.api.v1.team;

import com.polbohub.model.Sport;
import com.polbohub.service.team.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class TeamController implements TeamApi {

    private final TeamService service;

    public TeamController(TeamService service) {
        this.service = service;
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public TeamDto create(TeamDto dto) {
        return toResponse(service.create(toDto(dto)));
    }

    @Override
    public TeamDto get(UUID id) {
        return toResponse(service.get(id));
    }

    @Override
    public List<TeamDto> list() {
        return service.list().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public TeamDto update(UUID id, TeamDto dto) {
        return toResponse(service.update(id, toDto(dto)));
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(UUID id) {
        service.delete(id);
    }

    private com.polbohub.model.Team toDto(TeamDto request) {
        return new com.polbohub.model.Team(
                request.getId(),
                request.getName(),
                Sport.valueOf(request.getSport().name())
        );
    }

    private TeamDto toResponse(com.polbohub.model.Team dto) {
        TeamDto response = new TeamDto();
        response.setId(dto.id());
        response.setName(dto.name());
        response.setSport(com.polbohub.api.v1.Sport.valueOf(dto.sport().name()));
        return response;
    }
}
